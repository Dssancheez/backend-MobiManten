package com.mobimanten.backend.Mobimanten.dto.input;

import java.util.List;

public record MantenimientoInput(
        List<String> aplicaA,
        String tarea,
        String seccion,
        Integer intervaloKm,
        Integer intervaloMeses,
        List<RepuestoInput> opcionesRepuestos
) {}