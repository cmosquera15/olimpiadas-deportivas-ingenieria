package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Fase;

public  record FaseDTO (
    String nombre 
){
    public FaseDTO(Fase e) {
        this(
            e.getNombre() !=null ? e.getNombre() : null);
    }
}
