package com.mobimanten.backend.Mobimanten.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "garaje")
public class GarajeDocument {

    @Id
    private String id;
    private String usuarioId;
    private String cocheId;

    private String apodo;
    private Integer kilometrajeActual;
    private LocalDate fechaUltimaActualizacionKm;
}
