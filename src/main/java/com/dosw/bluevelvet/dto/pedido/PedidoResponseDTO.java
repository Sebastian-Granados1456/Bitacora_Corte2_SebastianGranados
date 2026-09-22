package com.dosw.bluevelvet.dto.pedido;

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
public class PedidoResponseDTO {

    private Long id;
    private Long idMesa;
    private List<ItemPedidoResponseDTO> items;
    private String estado;
    private LocalDateTime timestamp;
    private Double total;
}
