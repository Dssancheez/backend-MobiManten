package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.CochesDtoInput;
import com.mobimanten.backend.Mobimanten.dto.output.CochesListOutput;
import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import com.mobimanten.backend.Mobimanten.repository.CochesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocheService implements ICocheService{

    @Autowired
    private CochesRepository cochesRepository;


    public CochesListOutput toOutput (CochesDocument doc){
        return  new CochesListOutput(
                doc.getId(),
                doc.getModelo(),
                doc.getMarca(),
                doc.getMotor(),
                doc.getAnio(),
                doc.getImagenUrl(),
                doc.getTipo()
        );
    }
    public CochesDocument toDocument (CochesDtoInput input){
        CochesDocument doc = new CochesDocument();
        doc.setModelo(input.modelo());
        doc.setMarca(input.marca());
        doc.setMotor(input.motor());
        doc.setAnio(input.anio());
        doc.setImagenUrl(input.imagenUrl());
        doc.setTipo(input.tipo());
        doc.setDelete(false);
        return doc;
    }

    
    @Override
    public List<CochesListOutput> getCoches (){
        return cochesRepository.findByIsDeleteFalse()
                .stream()
                .map(this::toOutput)
                .toList();
    }

    @Override
    public CochesListOutput crearCoche(CochesDtoInput input) {

        CochesDocument doc = toDocument(input);
        CochesDocument saved =  cochesRepository.save(doc);
        return toOutput(saved);
    }


}
