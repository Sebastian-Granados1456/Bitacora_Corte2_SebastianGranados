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

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.service.mesa.IMesaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mesas")
@Tag(name = "Mesas", description = "Gestion de mesas del restaurante")
@RequiredArgsConstructor
public class MesaController {

    private final IMesaService mesaService;

    @Operation(summary = "Obtener todas las mesas")
    @GetMapping
    public List<MesaResponseDTO> obtenerTodas() {
        return mesaService.obtenerTodas();
    }

    @Operation(summary = "Obtener mesa por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mesa encontrada"),
            @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    @GetMapping("/{id}")
    public MesaResponseDTO buscarPorId(@PathVariable Long id) {
        return mesaService.buscarPorId(id);
    }

    @Operation(summary = "Crear una mesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mesa creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe una mesa con ese numero")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MesaResponseDTO crear(@RequestBody @Valid MesaRequestDTO dto) {
        return mesaService.crear(dto);
    }

    @Operation(summary = "Eliminar una mesa")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mesa eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
    }
}
