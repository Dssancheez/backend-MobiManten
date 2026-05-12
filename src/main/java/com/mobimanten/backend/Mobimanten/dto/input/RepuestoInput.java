package com.mobimanten.backend.Mobimanten.dto.input;

public record RepuestoInput(
        String nombre,
        String marca,
        Integer duracionKm,
        Integer duracionMeses,
        String enlaceCompra
) {}
