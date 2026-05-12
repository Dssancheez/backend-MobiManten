package com.mobimanten.backend.Mobimanten.controller;


import com.mobimanten.backend.Mobimanten.dto.input.GarajeInput;
import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import com.mobimanten.backend.Mobimanten.repository.CochesRepository;
import com.mobimanten.backend.Mobimanten.repository.GarajeRepository;
import com.mobimanten.backend.Mobimanten.service.IGarajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class garajeController {

    @Autowired
    private IGarajeService garajeService;


    @Autowired
    private CochesRepository cochesRepository;

    @MutationMapping
    public GarajeDocument anadirCocheGaraje(@Argument GarajeInput  input) {
        return garajeService.anadirCoche(input);
    }

    @QueryMapping
    public List<GarajeDocument> obtenerMiGaraje(@Argument("usuarioId") String usuarioId) {
        return garajeService.obtenerMiGaraje(usuarioId);
    }


    @SchemaMapping(typeName = "GarajeOutput", field = "coche")
    public CochesDocument obtenerDetallesDelCoche(GarajeDocument garaje) {
        return cochesRepository.findById(garaje.getCocheId()).orElse(null);
    }

    @MutationMapping
    public Boolean eliminarCocheDeGaraje(@Argument String usuarioId, @Argument String cocheId) {
        try {
            garajeService.eliminarDelGaraje(usuarioId, cocheId);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar del garaje: " + e.getMessage());
            return false;
        }
    }
}
