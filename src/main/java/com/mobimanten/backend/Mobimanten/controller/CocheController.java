package com.mobimanten.backend.Mobimanten.controller;


import com.mobimanten.backend.Mobimanten.dto.input.CochesDtoInput;
import com.mobimanten.backend.Mobimanten.dto.output.CochesListOutput;
import com.mobimanten.backend.Mobimanten.service.ICocheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


import java.util.List;

@Controller
public class CocheController {

    @Autowired
    private ICocheService cocheService;

    @QueryMapping
    public List<CochesListOutput> getCoches (){
        return cocheService.getCoches();
    }

    @MutationMapping
    public CochesListOutput crearCoche(@Argument CochesDtoInput input){
        return cocheService.crearCoche(input);

    }
}
