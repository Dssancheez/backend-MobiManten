package com.mobimanten.backend.Mobimanten.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepuestoOpcion {
    private String nombre;
    private String marca;
    private Integer duracionKm;
    private Integer duracionMeses;
    private String enlaceCompra;
}
