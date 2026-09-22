package com.dosw.bluevelvet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.controller.docs.CuentaApi;
import com.dosw.bluevelvet.dto.cuenta.CuentaRequestDTO;
import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;
import com.dosw.bluevelvet.mapper.cuenta.CuentaMapper;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.service.cuenta.ICuentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaController implements CuentaApi {

    private final ICuentaService cuentaService;
    private final CuentaMapper cuentaMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<CuentaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(cuentaMapper.toResponseList(cuentaService.obtenerTodas()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> buscarPorId(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.obtenerPorId(id);
        return ResponseEntity.ok(cuentaMapper.toResponse(cuenta));
    }

    @Override
    @PostMapping
    public ResponseEntity<CuentaResponseDTO> abrir(@RequestBody @Valid CuentaRequestDTO dto) {
        Cuenta creada = cuentaService.abrir(dto.getIdMesa());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cuentaMapper.toResponse(creada));
    }
}
