package com.dosw.bluevelvet.dto.pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Un item dentro de un pedido: referencia al plato y la cantidad solicitada.
 */
public record ItemPedidoRequestDTO(

        @NotNull(message = "El id del plato es obligatorio")
        Long idPlato,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad
) {
}
