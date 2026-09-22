package com.dosw.bluevelvet.validator.plato;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Plato;

// El Validator no tiene dependencias — se prueba sin mocks
class PlatoValidatorTest {

    private final PlatoValidator validator = new PlatoValidator();

    @Test
    @DisplayName("validarNombreUnico - nombre nuevo no lanza excepcion")
    void validarNombreUnico_nombreNuevo_noLanza() {
        List<Plato> existentes = List.of(Plato.builder().nombre("Ajiaco").build());

        assertDoesNotThrow(() -> validator.validarNombreUnico("Bandeja Paisa", existentes));
    }

    @Test
    @DisplayName("validarNombreUnico - nombre duplicado (case-insensitive) lanza RecursoDuplicadoException")
    void validarNombreUnico_nombreDuplicado_lanzaExcepcion() {
        List<Plato> existentes = List.of(Plato.builder().nombre("Ajiaco").build());

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarNombreUnico("AJIACO", existentes));
    }
}
