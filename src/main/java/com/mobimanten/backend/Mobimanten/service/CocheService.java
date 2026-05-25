package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.CochesDtoInput;
import com.mobimanten.backend.Mobimanten.dto.output.CochesListOutput;
import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import com.mobimanten.backend.Mobimanten.repository.CochesRepository;
import com.mobimanten.backend.Mobimanten.repository.CochesRepository;
import com.mobimanten.backend.Mobimanten.mapper.CocheMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocheService implements ICocheService{

    @Autowired
    private CochesRepository cochesRepository;

    @Autowired
    private CocheMapper cocheMapper;
    
    @Override
    public List<CochesListOutput> getCoches (){
        return cochesRepository.findByIsDeleteFalse()
                .stream()
                .map(cocheMapper::toOutput)
                .toList();
    }

    @Override
    public CochesListOutput crearCoche(CochesDtoInput input) {

        CochesDocument doc = cocheMapper.toDocument(input);
        CochesDocument saved =  cochesRepository.save(doc);
        return cocheMapper.toOutput(saved);
    }


}
