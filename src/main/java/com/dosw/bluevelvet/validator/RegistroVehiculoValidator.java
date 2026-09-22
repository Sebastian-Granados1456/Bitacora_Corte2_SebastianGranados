package com.dosw.bluevelvet.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

/**
 * Regla de negocio propia del registro de vehiculos en el parqueadero.
 */
@Component
public class RegistroVehiculoValidator {

    /**
     * Un vehiculo no puede registrar una nueva entrada si ya tiene un
     * registro activo (sin salida) con la misma placa.
     */
    public void validarSinRegistroActivo(Map<Long, RegistroVehiculo> registros, String placa) {
        boolean tieneRegistroActivo = registros.values().stream()
                .anyMatch(r -> r.getPlaca().equalsIgnoreCase(placa) && r.getSalida() == null);

        if (tieneRegistroActivo) {
            throw new RecursoDuplicadoException("El vehiculo con placa " + placa + " ya tiene un registro activo");
        }
    }
}
