package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Menu", description = "Carta publica del restaurante, sin autenticacion")
public interface MenuApi {

    @Operation(summary = "Consultar el menu", description = "Lista solo los platos disponibles")
    @ApiResponse(responseCode = "200", description = "Platos disponibles")
    ResponseEntity<List<PlatoResponseDTO>> obtenerMenu();
}
