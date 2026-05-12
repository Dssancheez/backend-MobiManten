package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CochesRepository extends MongoRepository<CochesDocument, String> {

    List<CochesDocument> findByIsDeleteFalse();

    Optional<CochesDocument> findByIdAndIsDeleteFalse(String id);

    List<CochesDocument> findByMarcaAndIsDeleteFalse(String marca);
}
