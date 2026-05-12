package com.mobimanten.backend.Mobimanten.repository;

import com.mobimanten.backend.Mobimanten.model.UsuariosDocument;
import lombok.Data;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuariosDocument, String> {

    Optional<UsuariosDocument> findByEmail(String email);


}

