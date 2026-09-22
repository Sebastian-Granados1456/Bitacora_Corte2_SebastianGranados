package com.dosw.bluevelvet.model.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    private Long id;
    private Long idMesa;
    private List<ItemPedido> items;
    private EstadoPedido estado;
    private LocalDateTime timestamp;

    /**
     * Un pedido solo puede modificarse mientras esta en estado RECIBIDO.
     * Una vez entra a preparacion, ya no se puede alterar su contenido.
     */
    public boolean puedeModificarse() {
        return estado == EstadoPedido.RECIBIDO;
    }

    public double calcularTotal() {
        if (items == null) {
            return 0.0;
        }
        return items.stream().mapToDouble(ItemPedido::subtotal).sum();
    }
}
