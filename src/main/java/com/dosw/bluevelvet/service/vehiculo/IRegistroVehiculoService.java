package com.dosw.bluevelvet.service.vehiculo;

import java.util.List;

import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

public interface IRegistroVehiculoService {

    List<RegistroVehiculo> obtenerTodos();

    RegistroVehiculo obtenerPorId(Long id);

    RegistroVehiculo registrarEntrada(RegistroVehiculo registro);
}
