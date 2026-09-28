package com.dosw.bluevelvet.validator.mesa;

import org.springframework.stereotype.Component;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.MesaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Regla de negocio propia de las mesas de Blue Velvet. Ya no carga todas
 * las mesas a memoria: le pregunta al Repository, que resuelve la consulta
 * con un indice en la columna "numero".
 */
@Component
@RequiredArgsConstructor
public class MesaValidator implements IMesaValidator {

    private final MesaRepository mesaRepository;

    @Override
    public void validarNumeroUnico(Integer numero) {
        if (mesaRepository.existsByNumero(numero)) {
            throw new RecursoDuplicadoException("Ya existe una mesa con el numero: " + numero);
        }
    }
}
