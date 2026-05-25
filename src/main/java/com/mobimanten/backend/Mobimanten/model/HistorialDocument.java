package com.mobimanten.backend.Mobimanten.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@Document(collection = "historial_mantenimientos")
public class HistorialDocument {

    @Id
    private String id;
    private String usuarioId;
    private String cocheGarajeId;
    private String cocheApodo; 
    private String mantenimientoId; 
    private String tarea;

    private LocalDate fechaRealizado;
    private Integer kilometrosRealizado;
    private Double coste;
    private String taller;
    private String observaciones;

    private Integer proximoCambioKm;
    private LocalDate proximoCambioFecha;

    private RepuestoOpcion repuestoSeleccionado;
}
