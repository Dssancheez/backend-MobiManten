package com.mobimanten.backend.Mobimanten.controller;

import com.mobimanten.backend.Mobimanten.dto.input.LoginInput;
import com.mobimanten.backend.Mobimanten.dto.input.RegistroInput;
import com.mobimanten.backend.Mobimanten.dto.output.AuthResponse;
import com.mobimanten.backend.Mobimanten.model.UsuariosDocument;
import com.mobimanten.backend.Mobimanten.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.stereotype.Controller;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GraphQlExceptionHandler
    public GraphQLError handle(RuntimeException ex) {
        return GraphqlErrorBuilder.newError()
                .errorType(graphql.ErrorClassification.errorClassification("BAD_REQUEST"))
                .message(ex.getMessage())
                .build();
    }

    @MutationMapping
    public UsuariosDocument registrarUsuario(@Argument RegistroInput input) {
        return usuarioService.registrarUsuario(input);
    }

    @MutationMapping
    public AuthResponse login(@Argument LoginInput input) {
        return usuarioService.login(input);
    }

    @MutationMapping
    public AuthResponse loginConGoogle(@Argument String idToken) {
        return usuarioService.loginConGoogle(idToken);
    }

    @MutationMapping
    public UsuariosDocument actualizarUsuario(@Argument String id, @Argument String nombre, @Argument String avatar) {
        return usuarioService.actualizarUsuario(id, nombre, avatar);
    }
}
