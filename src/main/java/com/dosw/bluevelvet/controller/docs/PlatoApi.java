package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Toda la documentacion Swagger del dominio Plato vive aqui, separada de la
 * implementacion. PlatoController implementa esta interfaz y queda limpio,
 * sin anotaciones de documentacion.
 */
@Tag(name = "Platos", description = "Gestion de la carta del restaurante")
public interface PlatoApi {

    @Operation(summary = "Obtener todos los platos")
    @ApiResponse(responseCode = "200", description = "Lista de platos")
    ResponseEntity<List<PlatoResponseDTO>> obtenerTodos();

    @Operation(summary = "Obtener plato por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato encontrado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    ResponseEntity<PlatoResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Crear un plato", description = "Agrega un nuevo plato a la carta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plato creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un plato con ese nombre")
    })
    ResponseEntity<PlatoResponseDTO> crear(@Valid PlatoRequestDTO dto);

    @Operation(summary = "Eliminar un plato")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Plato eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    ResponseEntity<Void> eliminar(Long id);
}
