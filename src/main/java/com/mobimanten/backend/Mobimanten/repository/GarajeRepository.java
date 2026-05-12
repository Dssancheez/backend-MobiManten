package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.GarajeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GarajeRepository extends MongoRepository<GarajeDocument, String> {

    List<GarajeDocument> findByUsuarioId(String usuarioId);

    void deleteByUsuarioIdAndCocheId(String usuarioId, String cocheId);
}
