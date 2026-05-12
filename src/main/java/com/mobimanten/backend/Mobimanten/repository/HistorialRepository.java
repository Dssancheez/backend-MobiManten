package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.HistorialDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialRepository extends MongoRepository<HistorialDocument, String> {
    java.util.List<HistorialDocument> findByCocheGarajeId(String cocheGarajeId);
    java.util.List<HistorialDocument> findByUsuarioId(String usuarioId);

}