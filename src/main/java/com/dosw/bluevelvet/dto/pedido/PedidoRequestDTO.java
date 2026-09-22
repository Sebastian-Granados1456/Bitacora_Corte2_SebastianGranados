package com.dosw.bluevelvet.dto.pedido;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada para levantar un nuevo pedido sobre una mesa.
 */
public record PedidoRequestDTO(

        @NotNull(message = "El id de la mesa es obligatorio")
        Long idMesa,

        @NotEmpty(message = "El pedido debe tener al menos un item")
        @Valid
        List<ItemPedidoRequestDTO> items
) {
}
