package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.PosicionDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.PosicionService;

@RestController
@RequestMapping("/api/posiciones")
public class PosicionController {

    private final PosicionService posicionService;

    public PosicionController(PosicionService posicionService) {
        this.posicionService = posicionService;
    }

    @GetMapping
    public List<PosicionDTO> obtenerPosiciones() {
        return posicionService.obtenerPosiciones();
    }
}
