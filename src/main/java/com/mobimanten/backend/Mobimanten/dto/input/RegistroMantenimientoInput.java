package com.mobimanten.backend.Mobimanten.dto.input;

public record RegistroMantenimientoInput(
        String usuarioId,
        String cocheGarajeId,
        String mantenimientoId,
        String tarea,
        String fechaRealizado,
        Integer kilometrosRealizado,
        Double coste,
        String taller,
        String observaciones,
        RepuestoInput repuestoSeleccionado
) {}
