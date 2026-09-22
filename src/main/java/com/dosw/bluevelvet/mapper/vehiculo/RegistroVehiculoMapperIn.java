package com.dosw.bluevelvet.mapper.vehiculo;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoRequestDTO;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

@Component
public class RegistroVehiculoMapperIn {

    public RegistroVehiculo toDomain(RegistroVehiculoRequestDTO dto) {
        RegistroVehiculo registro = new RegistroVehiculo();
        registro.setPlaca(dto.placa().toUpperCase());
        registro.setEntrada(LocalDateTime.now());
        return registro;
    }
}
