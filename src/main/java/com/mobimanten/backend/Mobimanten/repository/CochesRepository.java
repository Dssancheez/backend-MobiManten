package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.CochesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CochesRepository extends MongoRepository<CochesDocument, String> {

    List<CochesDocument> findByIsDeleteFalse();

    java.util.Optional<CochesDocument> findByIdAndIsDeleteFalse(String id);

    List<CochesDocument> findByMarcaAndIsDeleteFalse(String marca);
}
