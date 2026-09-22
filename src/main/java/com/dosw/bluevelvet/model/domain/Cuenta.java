package com.dosw.bluevelvet.model.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    private Long id;
    private Long idMesa;
    private Double total;
    private EstadoCuenta estado;
    private LocalDateTime fechaApertura;
    private List<Pedido> pedidos;

    /**
     * El total de la cuenta es la suma de los subtotales de todos los items
     * de todos los pedidos asociados a la mesa.
     */
    public Double calcularTotal() {
        if (pedidos == null) {
            return 0.0;
        }
        return pedidos.stream()
                .filter(pedido -> pedido.getItems() != null)
                .flatMap(pedido -> pedido.getItems().stream())
                .mapToDouble(ItemPedido::subtotal)
                .sum();
    }
}
