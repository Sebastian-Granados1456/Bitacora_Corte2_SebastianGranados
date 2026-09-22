package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.cuenta.CuentaRequestDTO;
import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Cuentas", description = "Gestion de cuentas y calculo del total por mesa")
public interface CuentaApi {

    @Operation(summary = "Obtener todas las cuentas")
    ResponseEntity<List<CuentaResponseDTO>> obtenerTodas();

    @Operation(summary = "Obtener cuenta por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    ResponseEntity<CuentaResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Abrir una cuenta", description = "Abre una cuenta para una mesa que no tenga otra cuenta abierta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta abierta correctamente"),
            @ApiResponse(responseCode = "404", description = "La mesa no existe"),
            @ApiResponse(responseCode = "409", description = "La mesa ya tiene una cuenta abierta")
    })
    ResponseEntity<CuentaResponseDTO> abrir(@Valid CuentaRequestDTO dto);
}
