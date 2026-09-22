package com.dosw.bluevelvet.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    private Long idPlato;
    private String nombrePlato;
    private Double precioCongelado;
    private Integer cantidad;

    /**
     * El precio del item se congela al momento del pedido, por lo que el
     * subtotal nunca depende del precio actual del plato.
     */
    public Double subtotal() {
        if (precioCongelado == null || cantidad == null) {
            return 0.0;
        }
        return precioCongelado * cantidad;
    }
}
