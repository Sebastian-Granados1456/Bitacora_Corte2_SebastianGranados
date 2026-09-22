package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Parqueadero", description = "Registro de vehiculos asociado a la atencion del restaurante")
public interface RegistroVehiculoApi {

    @Operation(summary = "Obtener todos los registros")
    ResponseEntity<List<RegistroVehiculoResponseDTO>> obtenerTodos();

    @Operation(summary = "Obtener registro por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    ResponseEntity<RegistroVehiculoResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Registrar entrada de un vehiculo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrada registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "La placa ya tiene un registro activo")
    })
    ResponseEntity<RegistroVehiculoResponseDTO> registrarEntrada(@Valid RegistroVehiculoRequestDTO dto);
}
