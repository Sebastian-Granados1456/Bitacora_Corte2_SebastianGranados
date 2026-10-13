package com.dosw.bluevelvet.validator.cuenta;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;
import com.dosw.bluevelvet.repository.CuentaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CuentaValidator implements ICuentaValidator {

    private final CuentaRepository cuentaRepository;

    @Override
    public void validarSinCuentaAbierta(Long idMesa) {
        if (cuentaRepository.existsByMesaIdAndEstado(idMesa, EstadoCuenta.ABIERTA)) {
            throw new RecursoDuplicadoException("La mesa " + idMesa + " ya tiene una cuenta abierta");
        }
    }
}
