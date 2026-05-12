package com.mobimanten.backend.Mobimanten.dto.input;

public record GarajeInput(
        String usuarioId,
        String cocheId,
        String apodo,
        Integer kilometrajeActual
) {}