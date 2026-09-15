package com.dosw.bluevelvet.model.domain;

import java.time.LocalDateTime;

public class Cuenta {

    private Long id;
    private Long idMesa;
    private Double total;
    private EstadoCuenta estado;
    private LocalDateTime fechaApertura;

    public Double calcularTotal() {
        return null;
    }
}
