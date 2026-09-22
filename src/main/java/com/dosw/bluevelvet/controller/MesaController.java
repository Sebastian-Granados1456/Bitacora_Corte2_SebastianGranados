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

import com.dosw.bluevelvet.controller.docs.MesaApi;
import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.mapper.mesa.MesaMapper;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.service.mesa.IMesaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mesas")
@RequiredArgsConstructor
public class MesaController implements MesaApi {

    private final IMesaService mesaService;
    private final MesaMapper mesaMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<MesaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mesaMapper.toResponseList(mesaService.obtenerTodas()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDTO> buscarPorId(@PathVariable Long id) {
        Mesa mesa = mesaService.obtenerPorId(id);
        return ResponseEntity.ok(mesaMapper.toResponse(mesa));
    }

    @Override
    @PostMapping
    public ResponseEntity<MesaResponseDTO> crear(@RequestBody @Valid MesaRequestDTO dto) {
        Mesa mesa = mesaMapper.toDomain(dto);
        Mesa creada = mesaService.crear(mesa);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mesaMapper.toResponse(creada));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
