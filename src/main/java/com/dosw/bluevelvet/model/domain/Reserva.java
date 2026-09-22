package com.dosw.bluevelvet.model.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    private Long id;
    private Long idMesa;
    private String cliente;
    private LocalDateTime fechaHora;
    private Integer comensales;

    /**
     * Una reserva esta vigente si su fecha y hora todavia no han pasado.
     */
    public boolean estaVigente() {
        return fechaHora != null && fechaHora.isAfter(LocalDateTime.now());
    }
}
