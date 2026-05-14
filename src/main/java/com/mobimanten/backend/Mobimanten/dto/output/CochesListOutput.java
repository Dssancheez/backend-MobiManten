package com.mobimanten.backend.Mobimanten.dto.output;

public record CochesListOutput(

        String id,
        String modelo,
        String marca,
        String motor,
        String combustible,
        Integer anio,
        String imagen,
        String tipo

) {
}
