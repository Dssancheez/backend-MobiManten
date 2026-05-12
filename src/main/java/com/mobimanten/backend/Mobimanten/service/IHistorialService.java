package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.RegistroMantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.HistorialDocument;

import java.util.List;

public interface IHistorialService {
    HistorialDocument registrarMantenimientoInteligente(RegistroMantenimientoInput input);
    List<HistorialDocument> obtenerHistorialPorCoche(String cocheGarajeId);
    List<HistorialDocument> obtenerHistorialUsuario(String usuarioId);
}
