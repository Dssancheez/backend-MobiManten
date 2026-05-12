package com.mobimanten.backend.Mobimanten.controller;

import com.mobimanten.backend.Mobimanten.dto.input.MantenimientoInput;
import com.mobimanten.backend.Mobimanten.dto.input.RegistroMantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.HistorialDocument;
import com.mobimanten.backend.Mobimanten.model.MantenimientoDocument;
import com.mobimanten.backend.Mobimanten.service.IHistorialService;
import com.mobimanten.backend.Mobimanten.service.IMantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MantenimientoController {

    @Autowired
    private IMantenimientoService mantenimientoService;

    @Autowired
    private IHistorialService historialService;

    @MutationMapping
    public MantenimientoDocument anadirMantenimiento(@Argument("input") MantenimientoInput input) {
        return mantenimientoService.anadirMantenimiento(input);
    }

    @QueryMapping
    public List<MantenimientoDocument> obtenerMantenimientosPorCoche(@Argument("cocheId") String cocheId) {
        return mantenimientoService.obtenerMantenimientosPorCoche(cocheId);
    }

    @QueryMapping
    public List<MantenimientoDocument> obtenerMantenimientosRecomendados(@Argument("cocheGarajeId") String cocheGarajeId) {
        return mantenimientoService.obtenerMantenimientosRecomendados(cocheGarajeId);
    }

    @MutationMapping
    public HistorialDocument registrarMantenimiento(@Argument("input") RegistroMantenimientoInput input) {
        return historialService.registrarMantenimientoInteligente(input);
    }

    @QueryMapping
    public List<HistorialDocument> obtenerHistorialPorCoche(@Argument("cocheGarajeId") String cocheGarajeId) {
        return historialService.obtenerHistorialPorCoche(cocheGarajeId);
    }

    @QueryMapping
    public List<HistorialDocument> obtenerHistorialUsuario(@Argument("usuarioId") String usuarioId) {
        return historialService.obtenerHistorialUsuario(usuarioId);
    }
}