package com.dosw.bluevelvet.model.domain;

import java.time.LocalDateTime;

public class Reserva {

    private Long id;
    private Long idMesa;
    private String cliente;
    private LocalDateTime fechaHora;
    private Integer comensales;

    public Boolean estaVigente() {
        return null;
    }
}
