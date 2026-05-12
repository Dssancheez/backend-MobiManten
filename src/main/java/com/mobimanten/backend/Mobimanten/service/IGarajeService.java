package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.GarajeInput;
import com.mobimanten.backend.Mobimanten.model.GarajeDocument;

import java.util.List;

public interface IGarajeService {

    GarajeDocument anadirCoche(GarajeInput input);

    List<GarajeDocument> obtenerMiGaraje(String usuarioId);

    void eliminarDelGaraje(String usuarioId, String cocheId);
}
