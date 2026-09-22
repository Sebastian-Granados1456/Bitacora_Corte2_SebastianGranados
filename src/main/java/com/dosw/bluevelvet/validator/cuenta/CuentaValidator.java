package com.dosw.bluevelvet.validator.cuenta;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;

/**
 * Regla de negocio propia de las cuentas de Blue Velvet.
 */
@Component
public class CuentaValidator implements ICuentaValidator {

    @Override
    public void validarSinCuentaAbierta(Long idMesa, Collection<Cuenta> cuentasExistentes) {
        boolean existeAbierta = cuentasExistentes.stream()
                .anyMatch(c -> c.getIdMesa().equals(idMesa) && c.getEstado() == EstadoCuenta.ABIERTA);

        if (existeAbierta) {
            throw new RecursoDuplicadoException("La mesa " + idMesa + " ya tiene una cuenta abierta");
        }
    }
}
