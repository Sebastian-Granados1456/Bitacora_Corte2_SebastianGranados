package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Mesas", description = "Gestion de mesas del restaurante")
public interface MesaApi {

    @Operation(summary = "Obtener todas las mesas")
    ResponseEntity<List<MesaResponseDTO>> obtenerTodas();

    @Operation(summary = "Obtener mesa por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mesa encontrada"),
            @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    ResponseEntity<MesaResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Crear una mesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mesa creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe una mesa con ese numero")
    })
    ResponseEntity<MesaResponseDTO> crear(@Valid MesaRequestDTO dto);

    @Operation(summary = "Eliminar una mesa")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mesa eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    ResponseEntity<Void> eliminar(Long id);
}
