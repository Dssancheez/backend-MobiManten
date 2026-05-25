package com.mobimanten.backend.Mobimanten.mapper;

import com.mobimanten.backend.Mobimanten.dto.input.GarajeInput;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GarajeMapper {

    public GarajeDocument toDocument(GarajeInput input) {
        GarajeDocument nuevoRegistro = new GarajeDocument();
        nuevoRegistro.setUsuarioId(input.usuarioId());
        nuevoRegistro.setCocheId(input.cocheId());
        nuevoRegistro.setApodo(input.apodo());
        nuevoRegistro.setKilometrajeActual(input.kilometrajeActual());
        nuevoRegistro.setFechaUltimaActualizacionKm(LocalDate.now());
        return nuevoRegistro;
    }
}
