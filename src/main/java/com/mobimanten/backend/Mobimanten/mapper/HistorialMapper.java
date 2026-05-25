package com.mobimanten.backend.Mobimanten.mapper;

import com.mobimanten.backend.Mobimanten.dto.input.RegistroMantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.HistorialDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class HistorialMapper {

    public HistorialDocument toDocument(RegistroMantenimientoInput input) {
        HistorialDocument historial = new HistorialDocument();
        historial.setUsuarioId(input.usuarioId());
        historial.setCocheGarajeId(input.cocheGarajeId());
        historial.setMantenimientoId(input.mantenimientoId());
        historial.setTarea(input.tarea());
        historial.setFechaRealizado(LocalDate.parse(input.fechaRealizado()));
        historial.setKilometrosRealizado(input.kilometrosRealizado());
        historial.setCoste(input.coste());
        historial.setTaller(input.taller());
        historial.setObservaciones(input.observaciones());
        return historial;
    }
}
