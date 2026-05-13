package com.mobimanten.backend.Mobimanten.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mobimanten.backend.Mobimanten.dto.input.LoginInput;
import com.mobimanten.backend.Mobimanten.dto.input.RegistroInput;
import com.mobimanten.backend.Mobimanten.dto.output.AuthResponse;
import com.mobimanten.backend.Mobimanten.model.UsuariosDocument;
import com.mobimanten.backend.Mobimanten.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Value("${google.client.id}")
    private String googleClientId;

    public UsuariosDocument mapearUsuario(RegistroInput input) {
        UsuariosDocument usuario = new UsuariosDocument();
        usuario.setEmail(input.email());
        usuario.setNombre(input.nombre());
        String password = passwordEncoder.encode(input.password());
        usuario.setPassword(password);

        return usuario;

    }

    public UsuariosDocument registrarUsuario(RegistroInput input) {
        if (usuarioRepository.findByEmail(input.email()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        UsuariosDocument nuevoUsuario = mapearUsuario(input);

        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public AuthResponse login(LoginInput input) {
        UsuariosDocument usuario = usuarioRepository.findByEmail(input.email())
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));

        boolean passwordCorrecta = passwordEncoder.matches(input.password(), usuario.getPassword());

        if (!passwordCorrecta) {
            throw new RuntimeException("Error: Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponse(usuario, token);    }

    @Override
    public AuthResponse loginConGoogle(String idToken) {
        try {
            String trimmedClientId = googleClientId != null ? googleClientId.trim() : null;
            System.out.println("--- Inicio Verificación Google ---");
            System.out.println("Client ID configurado: [" + trimmedClientId + "]");
            
            if (idToken == null || idToken.isEmpty()) {
                throw new RuntimeException("El idToken recibido está vacío");
            }
            
            System.out.println("Longitud del token recibido: " + idToken.length());

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(trimmedClientId))
                    .build();

            GoogleIdToken googleIdToken = null;
            try {
                googleIdToken = verifier.verify(idToken);
            } catch (Exception e) {
                System.err.println("Excepción durante verifier.verify: " + e.getClass().getName() + " - " + e.getMessage());
                throw new RuntimeException("Fallo técnico al verificar el token: " + e.getMessage());
            }

            if (googleIdToken == null) {
                System.err.println("VERIFICACIÓN FALLIDA: verifier.verify(idToken) devolvió null.");
                System.err.println("Causa probable: El idToken no fue emitido para el Client ID configurado o ha expirado.");
                throw new RuntimeException("Token de Google inválido o audiencia (client_id) no coincide");
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            String nombre = (String) payload.get("name");
            
            System.out.println("Autenticación exitosa para: " + email);

            Optional<UsuariosDocument> usuarioExistente = usuarioRepository.findByEmail(email);
            UsuariosDocument usuario;

            if (usuarioExistente.isPresent()) {
                usuario = usuarioExistente.get();
                System.out.println("Usuario existente encontrado en DB: " + usuario.getId());
            } else {
                System.out.println("Registrando nuevo usuario desde Google: " + email);
                usuario = new UsuariosDocument();
                usuario.setEmail(email);
                usuario.setNombre(nombre);
                usuario.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                usuario = usuarioRepository.save(usuario);
                System.out.println("Nuevo usuario creado con ID: " + usuario.getId());
            }

            String token = jwtService.generateToken(usuario.getEmail());
            System.out.println("JWT generado con éxito para el cliente");
            System.out.println("--- Fin Verificación Google ---");
            return new AuthResponse(usuario, token);

        } catch (Exception e) {
            System.err.println("Error crítico en loginConGoogle: " + e.getMessage());
            if (!(e instanceof RuntimeException)) {
                e.printStackTrace();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UsuariosDocument actualizarUsuario(String id, String nombre, String avatar) {
        UsuariosDocument usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));

        if (nombre != null) {
            usuario.setNombre(nombre);
        }
        if (avatar != null) {
            usuario.setAvatar(avatar);
        }

        return usuarioRepository.save(usuario);
    }
}
