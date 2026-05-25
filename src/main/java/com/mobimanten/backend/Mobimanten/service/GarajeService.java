package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.GarajeInput;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import com.mobimanten.backend.Mobimanten.repository.GarajeRepository;
import com.mobimanten.backend.Mobimanten.mapper.GarajeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GarajeService implements IGarajeService{

    @Autowired
    private GarajeRepository garajeRepository;

    @Autowired
    private GarajeMapper garajeMapper;

    @Override
    public GarajeDocument anadirCoche(GarajeInput input){
        GarajeDocument nuevoRegistro = garajeMapper.toDocument(input);

        return garajeRepository.save(nuevoRegistro);

    }

    @Override
    public List<GarajeDocument> obtenerMiGaraje(String usuarioId) {
        return garajeRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public void eliminarDelGaraje(String usuarioId, String cocheId) {
        garajeRepository.deleteByUsuarioIdAndCocheId(usuarioId, cocheId);

    }


}
