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
            System.out.println("Verificando token de Google para Client ID: " + googleClientId);
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                System.err.println("Token de Google inválido (verifier.verify(idToken) devolvió null)");
                throw new RuntimeException("Error: Token de Google inválido");
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            String nombre = (String) payload.get("name");
            
            System.out.println("Usuario Google autenticado: " + email);

            Optional<UsuariosDocument> usuarioExistente = usuarioRepository.findByEmail(email);
            UsuariosDocument usuario;

            if (usuarioExistente.isPresent()) {
                usuario = usuarioExistente.get();
            } else {
                // Registro automático
                System.out.println("Registrando nuevo usuario desde Google: " + email);
                usuario = new UsuariosDocument();
                usuario.setEmail(email);
                usuario.setNombre(nombre);
                usuario.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                usuario = usuarioRepository.save(usuario);
            }

            String token = jwtService.generateToken(usuario.getEmail());
            return new AuthResponse(usuario, token);

        } catch (Exception e) {
            System.err.println("Error en loginConGoogle: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al autenticar con Google: " + e.getMessage());
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
