package com.mobimanten.backend.Mobimanten.mapper;

import com.mobimanten.backend.Mobimanten.dto.input.CochesDtoInput;
import com.mobimanten.backend.Mobimanten.dto.output.CochesListOutput;
import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import org.springframework.stereotype.Component;

@Component
public class CocheMapper {

    public CochesListOutput toOutput(CochesDocument doc) {
        return new CochesListOutput(
                doc.getId(),
                doc.getModelo(),
                doc.getMarca(),
                doc.getMotor(),
                doc.getCombustible(),
                doc.getAnio(),
                doc.getImagen(),
                doc.getTipo()
        );
    }

    public CochesDocument toDocument(CochesDtoInput input) {
        CochesDocument doc = new CochesDocument();
        doc.setModelo(input.modelo());
        doc.setMarca(input.marca());
        doc.setMotor(input.motor());
        doc.setCombustible(input.combustible());
        doc.setAnio(input.anio());
        doc.setImagen(input.imagen());
        doc.setTipo(input.tipo());
        doc.setDelete(false);
        return doc;
    }
}
