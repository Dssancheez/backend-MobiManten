package com.mobimanten.backend.Mobimanten.dto.output;

import com.mobimanten.backend.Mobimanten.model.UsuariosDocument;

public record AuthResponse(
        UsuariosDocument usuario,
        String token
) {
}
