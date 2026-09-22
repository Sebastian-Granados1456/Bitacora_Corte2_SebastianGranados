package com.dosw.bluevelvet.validator.vehiculo;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

/**
 * Regla de negocio propia del registro de vehiculos en el parqueadero.
 */
@Component
public class RegistroVehiculoValidator implements IRegistroVehiculoValidator {

    @Override
    public void validarSinRegistroActivo(String placa, Collection<RegistroVehiculo> registrosExistentes) {
        boolean tieneRegistroActivo = registrosExistentes.stream()
                .anyMatch(r -> r.getPlaca().equalsIgnoreCase(placa) && r.getSalida() == null);

        if (tieneRegistroActivo) {
            throw new RecursoDuplicadoException("El vehiculo con placa " + placa + " ya tiene un registro activo");
        }
    }
}
