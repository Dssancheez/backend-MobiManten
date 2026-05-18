package com.mobimanten.backend.Mobimanten.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "mantenimiento")
public class MantenimientoDocument {

    @Id
    private String id;

    private List<String> aplicaA; // ej: ["DIESEL", "GASOLINA", "TODOS"]
    private String tarea;
    private String seccion;
    private Integer intervaloKm;
    private Integer intervaloMeses;

    private Integer anioDesde;
    private Integer anioHasta;

    private List<RepuestoOpcion> opcionesRepuestos;
}
