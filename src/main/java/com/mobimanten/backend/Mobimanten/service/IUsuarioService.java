package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.LoginInput;
import com.mobimanten.backend.Mobimanten.dto.input.RegistroInput;
import com.mobimanten.backend.Mobimanten.dto.output.AuthResponse;
import com.mobimanten.backend.Mobimanten.model.UsuariosDocument;

public interface IUsuarioService {

    UsuariosDocument registrarUsuario(RegistroInput input);

    AuthResponse login(LoginInput input);
    AuthResponse loginConGoogle(String idToken);

    UsuariosDocument actualizarUsuario(String id, String nombre, String avatar);
}
