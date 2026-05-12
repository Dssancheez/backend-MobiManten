package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.MantenimientoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MantenimientoRepository extends MongoRepository<MantenimientoDocument, String> {

    List<MantenimientoDocument> findByAplicaAIn(List<String> etiquetas);
}
