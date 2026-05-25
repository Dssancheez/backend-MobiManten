package com.mobimanten.backend.Mobimanten.mapper;

import com.mobimanten.backend.Mobimanten.dto.input.RegistroInput;
import com.mobimanten.backend.Mobimanten.model.UsuariosDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioMapper {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuariosDocument toDocument(RegistroInput input) {
        UsuariosDocument usuario = new UsuariosDocument();
        usuario.setEmail(input.email());
        usuario.setNombre(input.nombre());
        usuario.setPassword(passwordEncoder.encode(input.password()));
        return usuario;
    }

    public UsuariosDocument toGoogleUserDocument(String email, String nombre) {
        UsuariosDocument usuario = new UsuariosDocument();
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        return usuario;
    }
}
