package com.dosw.bluevelvet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.dto.cuenta.CuentaRequestDTO;
import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;
import com.dosw.bluevelvet.service.cuenta.ICuentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cuentas")
@Tag(name = "Cuentas", description = "Gestion de cuentas y calculo del total por mesa")
@RequiredArgsConstructor
public class CuentaController {

    private final ICuentaService cuentaService;

    @Operation(summary = "Obtener todas las cuentas")
    @GetMapping
    public List<CuentaResponseDTO> obtenerTodas() {
        return cuentaService.obtenerTodas();
    }

    @Operation(summary = "Obtener cuenta por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{id}")
    public CuentaResponseDTO buscarPorId(@PathVariable Long id) {
        return cuentaService.buscarPorId(id);
    }

    @Operation(summary = "Abrir una cuenta", description = "Abre una cuenta para una mesa que no tenga otra cuenta abierta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta abierta correctamente"),
            @ApiResponse(responseCode = "404", description = "La mesa no existe"),
            @ApiResponse(responseCode = "409", description = "La mesa ya tiene una cuenta abierta")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponseDTO abrir(@RequestBody @Valid CuentaRequestDTO dto) {
        return cuentaService.abrir(dto);
    }
}
