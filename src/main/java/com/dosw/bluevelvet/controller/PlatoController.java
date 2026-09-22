package com.dosw.bluevelvet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.controller.docs.PlatoApi;
import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.mapper.plato.PlatoMapper;
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.service.plato.IPlatoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Endpoints para gestionar la carta de platos del restaurante. Sin logica
 * de negocio: solo recibe, traduce con el Mapper, delega al Service y
 * responde.
 */
@RestController
@RequestMapping("/api/v1/platos")
@RequiredArgsConstructor
@Slf4j
public class PlatoController implements PlatoApi {

    private final IPlatoService platoService;
    private final PlatoMapper platoMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> obtenerTodos() {
        List<Plato> platos = platoService.obtenerTodos();
        return ResponseEntity.ok(platoMapper.toResponseList(platos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> buscarPorId(@PathVariable Long id) {
        Plato plato = platoService.obtenerPorId(id);
        return ResponseEntity.ok(platoMapper.toResponse(plato));
    }

    @Override
    @PostMapping
    public ResponseEntity<PlatoResponseDTO> crear(@RequestBody @Valid PlatoRequestDTO dto) {
        log.info("POST /api/v1/platos - nombre={}", dto.getNombre());

        Plato plato = platoMapper.toDomain(dto);
        Plato creado = platoService.crear(plato);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(platoMapper.toResponse(creado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        platoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
