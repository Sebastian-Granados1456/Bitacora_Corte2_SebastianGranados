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

import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.service.pedido.IPedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Anotacion y seguimiento de pedidos con sus items")
@RequiredArgsConstructor
public class PedidoController {

    private final IPedidoService pedidoService;

    @Operation(summary = "Obtener todos los pedidos")
    @GetMapping
    public List<PedidoResponseDTO> obtenerTodos() {
        return pedidoService.obtenerTodos();
    }

    @Operation(summary = "Obtener pedidos de una mesa")
    @GetMapping("/mesa/{idMesa}")
    public List<PedidoResponseDTO> obtenerPorMesa(@PathVariable Long idMesa) {
        return pedidoService.obtenerPorMesa(idMesa);
    }

    @Operation(summary = "Obtener pedido por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @Operation(summary = "Crear un pedido", description = "Registra un nuevo pedido con sus items para una mesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Algun plato del pedido no existe")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO crear(@RequestBody @Valid PedidoRequestDTO dto) {
        return pedidoService.crear(dto);
    }
}
