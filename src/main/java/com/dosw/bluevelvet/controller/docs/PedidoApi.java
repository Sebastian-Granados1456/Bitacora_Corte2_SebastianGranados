package com.dosw.bluevelvet.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pedidos", description = "Anotacion y seguimiento de pedidos con sus items")
public interface PedidoApi {

    @Operation(summary = "Obtener todos los pedidos")
    ResponseEntity<List<PedidoResponseDTO>> obtenerTodos();

    @Operation(summary = "Obtener pedidos de una mesa")
    ResponseEntity<List<PedidoResponseDTO>> obtenerPorMesa(Long idMesa);

    @Operation(summary = "Obtener pedido por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    ResponseEntity<PedidoResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Crear un pedido", description = "Registra un nuevo pedido con sus items para una mesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "404", description = "Algun plato del pedido no existe")
    })
    ResponseEntity<PedidoResponseDTO> crear(@Valid PedidoRequestDTO dto);
}
