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

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.service.plato.IPlatoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoints para gestionar la carta de platos del restaurante.
 */
@RestController
@RequestMapping("/api/v1/platos")
@Tag(name = "Platos", description = "Gestion de la carta del restaurante")
@RequiredArgsConstructor
public class PlatoController {

    private final IPlatoService platoService;

    @Operation(summary = "Obtener todos los platos")
    @GetMapping
    public List<PlatoResponseDTO> obtenerTodos() {
        return platoService.obtenerTodos();
    }

    @Operation(summary = "Obtener plato por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato encontrado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    @GetMapping("/{id}")
    public PlatoResponseDTO buscarPorId(@PathVariable Long id) {
        return platoService.buscarPorId(id);
    }

    @Operation(summary = "Crear un plato", description = "Agrega un nuevo plato a la carta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plato creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un plato con ese nombre")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatoResponseDTO crear(@RequestBody @Valid PlatoRequestDTO dto) {
        return platoService.crear(dto);
    }

    @Operation(summary = "Eliminar un plato")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Plato eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        platoService.eliminar(id);
    }
}
