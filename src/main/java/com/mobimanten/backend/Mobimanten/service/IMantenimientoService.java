package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.MantenimientoInput;
import com.mobimanten.backend.Mobimanten.model.MantenimientoDocument;
import java.util.List;

public interface IMantenimientoService {
    MantenimientoDocument anadirMantenimiento(MantenimientoInput input);

    List<MantenimientoDocument> obtenerMantenimientosPorCoche(String cocheId);

    List<MantenimientoDocument> obtenerMantenimientosRecomendados(String cocheGarajeId);
}