package com.dosw.bluevelvet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;
import com.dosw.bluevelvet.service.reserva.IReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Gestion de reservas por mesa")
@RequiredArgsConstructor
public class ReservaController {

    private final IReservaService reservaService;

    @Operation(summary = "Obtener todas las reservas")
    @GetMapping
    public List<ReservaResponseDTO> obtenerTodas() {
        return reservaService.obtenerTodas();
    }

    @Operation(summary = "Obtener reserva por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ReservaResponseDTO buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id);
    }

    @Operation(summary = "Crear una reserva", description = "Reserva una mesa en una fecha y hora futura")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "La mesa no existe"),
            @ApiResponse(responseCode = "409", description = "Ya existe una reserva en ese horario")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO crear(@RequestBody @Valid ReservaRequestDTO dto) {
        return reservaService.crear(dto);
    }

    @Operation(summary = "Cancelar una reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
    }
}
