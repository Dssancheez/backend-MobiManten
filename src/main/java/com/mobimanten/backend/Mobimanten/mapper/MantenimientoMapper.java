package com.mobimanten.backend.Mobimanten.mapper;

import com.mobimanten.backend.Mobimanten.dto.input.MantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.MantenimientoDocument;
import com.mobimanten.backend.Mobimanten.model.RepuestoOpcion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MantenimientoMapper {

    public MantenimientoDocument toDocument(MantenimientoInput input) {
        MantenimientoDocument nuevoMantenimiento = new MantenimientoDocument();
        nuevoMantenimiento.setAplicaA(input.aplicaA());
        nuevoMantenimiento.setTarea(input.tarea());
        nuevoMantenimiento.setSeccion(input.seccion());
        nuevoMantenimiento.setIntervaloKm(input.intervaloKm());
        nuevoMantenimiento.setIntervaloMeses(input.intervaloMeses());
        nuevoMantenimiento.setAnioDesde(input.anioDesde());
        nuevoMantenimiento.setAnioHasta(input.anioHasta());

        if (input.opcionesRepuestos() != null) {
            List<RepuestoOpcion> opciones = input.opcionesRepuestos().stream()
                    .map(r -> new RepuestoOpcion(r.nombre(), r.marca(), r.duracionKm(), r.duracionMeses(), r.enlaceCompra()))
                    .collect(Collectors.toList());
            nuevoMantenimiento.setOpcionesRepuestos(opciones);
        }

        return nuevoMantenimiento;
    }
}
