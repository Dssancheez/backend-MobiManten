package com.mobimanten.backend.Mobimanten.service;

import com.mobimanten.backend.Mobimanten.dto.input.CochesDtoInput;
import com.mobimanten.backend.Mobimanten.dto.output.CochesListOutput;

import java.util.List;

public interface ICocheService {

    List<CochesListOutput> getCoches ();

    CochesListOutput crearCoche(CochesDtoInput input);
}
