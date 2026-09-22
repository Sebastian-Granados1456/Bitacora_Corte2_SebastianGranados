package com.dosw.bluevelvet.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;

/**
 * Regla de negocio propia de las cuentas de Blue Velvet.
 */
@Component
public class CuentaValidator {

    /**
     * Una mesa puede tener a lo sumo una cuenta abierta a la vez.
     */
    public void validarSinCuentaAbierta(Map<Long, Cuenta> cuentas, Long idMesa) {
        boolean existeAbierta = cuentas.values().stream()
                .anyMatch(c -> c.getIdMesa().equals(idMesa) && c.getEstado() == EstadoCuenta.ABIERTA);

        if (existeAbierta) {
            throw new RecursoDuplicadoException("La mesa " + idMesa + " ya tiene una cuenta abierta");
        }
    }
}
