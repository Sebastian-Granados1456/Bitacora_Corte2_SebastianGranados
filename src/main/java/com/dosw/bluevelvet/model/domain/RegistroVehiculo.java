package com.dosw.bluevelvet.model.domain;

import java.time.LocalDateTime;

public class RegistroVehiculo {

    private Long id;
    private String placa;
    private LocalDateTime entrada;
    private LocalDateTime salida;
    private Double cobro;

    public Double calcularCobro() {
        return null;
    }
}
