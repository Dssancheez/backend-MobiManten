package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.HistorialDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialRepository extends MongoRepository<HistorialDocument, String> {
    List<HistorialDocument> findByCocheGarajeId(String cocheGarajeId);
    List<HistorialDocument> findByUsuarioId(String usuarioId);

}