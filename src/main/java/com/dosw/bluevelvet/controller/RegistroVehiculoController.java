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

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;
import com.dosw.bluevelvet.service.vehiculo.IRegistroVehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Parqueadero", description = "Registro de vehiculos asociado a la atencion del restaurante")
@RequiredArgsConstructor
public class RegistroVehiculoController {

    private final IRegistroVehiculoService registroVehiculoService;

    @Operation(summary = "Obtener todos los registros")
    @GetMapping
    public List<RegistroVehiculoResponseDTO> obtenerTodos() {
        return registroVehiculoService.obtenerTodos();
    }

    @Operation(summary = "Obtener registro por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @GetMapping("/{id}")
    public RegistroVehiculoResponseDTO buscarPorId(@PathVariable Long id) {
        return registroVehiculoService.buscarPorId(id);
    }

    @Operation(summary = "Registrar entrada de un vehiculo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrada registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "La placa ya tiene un registro activo")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroVehiculoResponseDTO registrarEntrada(@RequestBody @Valid RegistroVehiculoRequestDTO dto) {
        return registroVehiculoService.registrarEntrada(dto);
    }
}
