package com.dosw.bluevelvet.mapper.cuenta;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;
import com.dosw.bluevelvet.model.domain.Cuenta;

@Component
public class CuentaMapperOut {

    public CuentaResponseDTO toDTO(Cuenta cuenta) {
        return new CuentaResponseDTO(
                cuenta.getId(),
                cuenta.getIdMesa(),
                cuenta.calcularTotal(),
                cuenta.getEstado().name(),
                cuenta.getFechaApertura()
        );
    }
}
