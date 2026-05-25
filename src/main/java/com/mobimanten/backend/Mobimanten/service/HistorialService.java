package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.RegistroMantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import com.mobimanten.backend.Mobimanten.model.HistorialDocument;
import com.mobimanten.backend.Mobimanten.model.RepuestoOpcion;
import com.mobimanten.backend.Mobimanten.repository.GarajeRepository;
import com.mobimanten.backend.Mobimanten.repository.HistorialRepository;
import com.mobimanten.backend.Mobimanten.repository.MantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistorialService implements IHistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private GarajeRepository garajeRepository;

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Override
    public HistorialDocument registrarMantenimientoInteligente(RegistroMantenimientoInput input) {
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


        if (input.repuestoSeleccionado() != null) {
            RepuestoOpcion repuesto = new RepuestoOpcion(
                    input.repuestoSeleccionado().nombre(),
                    input.repuestoSeleccionado().marca(),
                    input.repuestoSeleccionado().duracionKm(),
                    input.repuestoSeleccionado().duracionMeses(),
                    input.repuestoSeleccionado().enlaceCompra()
            );
            historial.setRepuestoSeleccionado(repuesto);


            if (repuesto.getDuracionKm() != null) {
                historial.setProximoCambioKm(input.kilometrosRealizado() + repuesto.getDuracionKm());
            }

            if (repuesto.getDuracionMeses() != null) {
                historial.setProximoCambioFecha(historial.getFechaRealizado().plusMonths(repuesto.getDuracionMeses()));
            }
        } else if (input.mantenimientoId() != null && !input.mantenimientoId().isEmpty()) {

            mantenimientoRepository.findById(input.mantenimientoId()).ifPresent(m -> {
                if (m.getIntervaloKm() != null) {
                    historial.setProximoCambioKm(input.kilometrosRealizado() + m.getIntervaloKm());
                }
                if (m.getIntervaloMeses() != null) {
                    historial.setProximoCambioFecha(historial.getFechaRealizado().plusMonths(m.getIntervaloMeses()));
                }
            });
        }


        garajeRepository.findById(input.cocheGarajeId()).ifPresent(garaje -> {
            historial.setCocheApodo(garaje.getApodo());
            garaje.setKilometrajeActual(input.kilometrosRealizado());
            garaje.setFechaUltimaActualizacionKm(LocalDate.now());
            garajeRepository.save(garaje);
        });

        return historialRepository.save(historial);
    }

    @Override
    public List<HistorialDocument> obtenerHistorialPorCoche(String cocheGarajeId) {
        return historialRepository.findByCocheGarajeId(cocheGarajeId);
    }

    @Override
    public List<HistorialDocument> obtenerHistorialUsuario(String usuarioId) {
        return historialRepository.findByUsuarioId(usuarioId);
    }
}
