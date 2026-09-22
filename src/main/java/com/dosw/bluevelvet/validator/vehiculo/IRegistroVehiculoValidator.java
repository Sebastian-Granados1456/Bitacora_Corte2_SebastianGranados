package com.dosw.bluevelvet.validator.vehiculo;

import java.util.Collection;

import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

public interface IRegistroVehiculoValidator {

    void validarSinRegistroActivo(String placa, Collection<RegistroVehiculo> registrosExistentes);
}
