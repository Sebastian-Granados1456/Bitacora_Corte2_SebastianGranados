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

import com.dosw.bluevelvet.controller.docs.RegistroVehiculoApi;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoMapper;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;
import com.dosw.bluevelvet.service.vehiculo.IRegistroVehiculoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vehiculos")
@RequiredArgsConstructor
public class RegistroVehiculoController implements RegistroVehiculoApi {

    private final IRegistroVehiculoService registroVehiculoService;
    private final RegistroVehiculoMapper registroVehiculoMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<RegistroVehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(registroVehiculoMapper.toResponseList(registroVehiculoService.obtenerTodos()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RegistroVehiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        RegistroVehiculo registro = registroVehiculoService.obtenerPorId(id);
        return ResponseEntity.ok(registroVehiculoMapper.toResponse(registro));
    }

    @Override
    @PostMapping
    public ResponseEntity<RegistroVehiculoResponseDTO> registrarEntrada(@RequestBody @Valid RegistroVehiculoRequestDTO dto) {
        RegistroVehiculo registro = registroVehiculoMapper.toDomain(dto);
        RegistroVehiculo creado = registroVehiculoService.registrarEntrada(registro);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registroVehiculoMapper.toResponse(creado));
    }
}
