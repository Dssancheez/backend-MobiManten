package com.mobimanten.backend.Mobimanten.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coches")

@Getter
@Setter
public class CochesDocument {


    @Id
    private String id;

    private String modelo;
    private String marca;
    private String motor;
    private String combustible;
    private Integer anio;
    private String tipo;
    private String imagen;
    private boolean isDelete = false;


}
