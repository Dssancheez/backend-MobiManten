package com.mobimanten.backend.Mobimanten.service;


import com.mobimanten.backend.Mobimanten.dto.input.MantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import com.mobimanten.backend.Mobimanten.model.MantenimientoDocument;
import com.mobimanten.backend.Mobimanten.model.RepuestoOpcion;
import com.mobimanten.backend.Mobimanten.repository.CochesRepository;
import com.mobimanten.backend.Mobimanten.repository.GarajeRepository;
import com.mobimanten.backend.Mobimanten.repository.MantenimientoRepository;
import com.mobimanten.backend.Mobimanten.repository.MantenimientoRepository;
import com.mobimanten.backend.Mobimanten.mapper.MantenimientoMapper;
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
    private MantenimientoMapper mantenimientoMapper;

    @Autowired
    private GarajeRepository garajeRepository;

    @Autowired
    private CochesRepository cochesRepository;


    @Override
    public MantenimientoDocument anadirMantenimiento(MantenimientoInput input) {
        MantenimientoDocument nuevoMantenimiento = mantenimientoMapper.toDocument(input);
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
        
        if (coche.getModelo() != null && !coche.getModelo().trim().isEmpty()) {
            String modeloLimpio = coche.getModelo().trim().split("\\s+")[0].toUpperCase();
            etiquetas.add("MODELO:" + modeloLimpio);
        }

        if (coche.getMarca() != null && coche.getMotor() != null) {
            etiquetas.add("MARCA:" + coche.getMarca().toUpperCase() + "_" + coche.getMotor().toUpperCase());
        }

        List<MantenimientoDocument> mantenimientos = mantenimientoRepository.findByAplicaAIn(etiquetas);
        int cocheAnio = coche.getAnio() != null ? coche.getAnio() : 0;

        return mantenimientos.stream().filter(m -> {
            if (cocheAnio == 0) return true;
            if (m.getAnioDesde() != null && cocheAnio < m.getAnioDesde()) return false;
            if (m.getAnioHasta() != null && cocheAnio > m.getAnioHasta()) return false;
            return true;
        }).collect(Collectors.toList());
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
        etiquetas.add("TODOS");

        int cocheAnio = 0;
        if (cocheOpt.isPresent()) {
            CochesDocument coche = cocheOpt.get();
            cocheAnio = coche.getAnio() != null ? coche.getAnio() : 0;
            if (coche.getMotor() != null) etiquetas.add(coche.getMotor().toUpperCase());
            if (coche.getCombustible() != null) etiquetas.add(coche.getCombustible().toUpperCase());
            if (coche.getTipo() != null) etiquetas.add(coche.getTipo().toUpperCase());
            if (coche.getMarca() != null) etiquetas.add("MARCA:" + coche.getMarca().toUpperCase());
            
            if (coche.getModelo() != null && !coche.getModelo().trim().isEmpty()) {
                String modeloLimpio = coche.getModelo().trim().split("\\s+")[0].toUpperCase();
                etiquetas.add("MODELO:" + modeloLimpio);
            }

            if (coche.getMarca() != null && coche.getMotor() != null) {
                etiquetas.add("MARCA:" + coche.getMarca().toUpperCase() + "_" + coche.getMotor().toUpperCase());
            }
        }

        List<MantenimientoDocument> mantenimientos = mantenimientoRepository.findByAplicaAIn(etiquetas);
        final int anio = cocheAnio;
        return mantenimientos.stream().filter(m -> {
            if (anio == 0) return true;
            if (m.getAnioDesde() != null && anio < m.getAnioDesde()) return false;
            if (m.getAnioHasta() != null && anio > m.getAnioHasta()) return false;
            return true;
        }).collect(Collectors.toList());
    }
}
