package com.dosw.bluevelvet.model.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroVehiculo {

    private static final double TARIFA_POR_HORA = 3000.0;

    private Long id;
    private String placa;
    private LocalDateTime entrada;
    private LocalDateTime salida;

    /**
     * El cobro se calcula por horas (o fraccion) transcurridas entre la
     * entrada y la salida del vehiculo, a una tarifa fija por hora.
     */
    public double calcularCobro() {
        if (entrada == null || salida == null || salida.isBefore(entrada)) {
            return 0.0;
        }
        long minutos = Duration.between(entrada, salida).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        return horas * TARIFA_POR_HORA;
    }
}
