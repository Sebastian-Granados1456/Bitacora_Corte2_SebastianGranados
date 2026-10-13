package com.dosw.bluevelvet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.controller.docs.MenuApi;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.mapper.plato.PlatoMapper;
import com.dosw.bluevelvet.service.plato.IPlatoService;

import lombok.RequiredArgsConstructor;

/**
 * Carta publica para los clientes. Reutiliza IPlatoService, el mismo que
 * usa PlatoController, sin duplicar logica.
 */
@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController implements MenuApi {

    private final IPlatoService platoService;
    private final PlatoMapper platoMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> obtenerMenu() {
        return ResponseEntity.ok(platoMapper.toResponseList(platoService.obtenerDisponibles()));
    }
}
