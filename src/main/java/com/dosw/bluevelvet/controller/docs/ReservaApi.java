package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Reservas", description = "Gestion de reservas por mesa")
public interface ReservaApi {

    @Operation(summary = "Obtener todas las reservas")
    ResponseEntity<List<ReservaResponseDTO>> obtenerTodas();

    @Operation(summary = "Obtener reserva por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    ResponseEntity<ReservaResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Crear una reserva", description = "Reserva una mesa en una fecha y hora futura")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "La mesa no existe"),
            @ApiResponse(responseCode = "409", description = "Ya existe una reserva en ese horario")
    })
    ResponseEntity<ReservaResponseDTO> crear(@Valid ReservaRequestDTO dto);

    @Operation(summary = "Cancelar una reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    ResponseEntity<Void> cancelar(Long id);
}
