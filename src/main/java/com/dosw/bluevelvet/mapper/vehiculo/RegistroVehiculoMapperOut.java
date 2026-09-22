package com.dosw.bluevelvet.mapper.vehiculo;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.vehiculo.RegistroVehiculoResponseDTO;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

@Component
public class RegistroVehiculoMapperOut {

    public RegistroVehiculoResponseDTO toDTO(RegistroVehiculo registro) {
        return new RegistroVehiculoResponseDTO(
                registro.getId(),
                registro.getPlaca(),
                registro.getEntrada()
        );
    }
}
