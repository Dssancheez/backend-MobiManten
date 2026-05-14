package com.mobimanten.backend.Mobimanten.service;


import com.mobimanten.backend.Mobimanten.dto.input.MantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import com.mobimanten.backend.Mobimanten.model.MantenimientoDocument;
import com.mobimanten.backend.Mobimanten.model.RepuestoOpcion;
import com.mobimanten.backend.Mobimanten.repository.CochesRepository;
import com.mobimanten.backend.Mobimanten.repository.GarajeRepository;
import com.mobimanten.backend.Mobimanten.repository.MantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MantenimientoService implements IMantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private GarajeRepository garajeRepository;

    @Autowired
    private CochesRepository cochesRepository;


    @Override
    public MantenimientoDocument anadirMantenimiento(MantenimientoInput input) {
        MantenimientoDocument nuevoMantenimiento = new MantenimientoDocument();
        nuevoMantenimiento.setAplicaA(input.aplicaA());
        nuevoMantenimiento.setTarea(input.tarea());
        nuevoMantenimiento.setIntervaloKm(input.intervaloKm());
        nuevoMantenimiento.setIntervaloMeses(input.intervaloMeses());
        
        if (input.opcionesRepuestos() != null) {
            List<RepuestoOpcion> opciones = input.opcionesRepuestos().stream()
                    .map(r -> new RepuestoOpcion(r.nombre(), r.marca(), r.duracionKm(), r.duracionMeses(), r.enlaceCompra()))
                    .collect(Collectors.toList());
            nuevoMantenimiento.setOpcionesRepuestos(opciones);
        }


        return mantenimientoRepository.save(nuevoMantenimiento);
    }

    @Override
    public List<MantenimientoDocument> obtenerMantenimientosPorCoche(String cocheId) {
        Optional<CochesDocument> cocheOpt = cochesRepository.findById(cocheId);
        if (cocheOpt.isEmpty()) {
            return new ArrayList<>();
        }

        CochesDocument coche = cocheOpt.get();
        List<String> etiquetas = new ArrayList<>();
        etiquetas.add("TODOS");

        if (coche.getMotor() != null) etiquetas.add(coche.getMotor().toUpperCase());
        if (coche.getCombustible() != null) etiquetas.add(coche.getCombustible().toUpperCase());
        if (coche.getTipo() != null) etiquetas.add(coche.getTipo().toUpperCase());
        if (coche.getMarca() != null) etiquetas.add("MARCA:" + coche.getMarca().toUpperCase());

        return mantenimientoRepository.findByAplicaAIn(etiquetas);
    }

    @Override
    public List<MantenimientoDocument> obtenerMantenimientosRecomendados(String cocheGarajeId) {
        Optional<GarajeDocument> garajeOpt = garajeRepository.findById(cocheGarajeId);
        if (garajeOpt.isEmpty()) {
            return new ArrayList<>();
        }

        GarajeDocument garaje = garajeOpt.get();
        Optional<CochesDocument> cocheOpt = cochesRepository.findById(garaje.getCocheId());
        
        List<String> etiquetas = new ArrayList<>();
        etiquetas.add("TODOS"); // Siempre incluir los universales

        if (cocheOpt.isPresent()) {
            CochesDocument coche = cocheOpt.get();
            if (coche.getMotor() != null) etiquetas.add(coche.getMotor().toUpperCase());
            if (coche.getCombustible() != null) etiquetas.add(coche.getCombustible().toUpperCase());
            if (coche.getTipo() != null) etiquetas.add(coche.getTipo().toUpperCase());
            if (coche.getMarca() != null) etiquetas.add("MARCA:" + coche.getMarca().toUpperCase());
        }

        return mantenimientoRepository.findByAplicaAIn(etiquetas);
    }
}
