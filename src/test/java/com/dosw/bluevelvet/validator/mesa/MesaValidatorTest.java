package com.dosw.bluevelvet.validator.mesa;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.Mesa;

class MesaValidatorTest {

    private final MesaValidator validator = new MesaValidator();

    @Test
    @DisplayName("validarNumeroUnico - numero nuevo no lanza excepcion")
    void validarNumeroUnico_numeroNuevo_noLanza() {
        List<Mesa> existentes = List.of(Mesa.builder().numero(1).build());

        assertDoesNotThrow(() -> validator.validarNumeroUnico(2, existentes));
    }

    @Test
    @DisplayName("validarNumeroUnico - numero duplicado lanza RecursoDuplicadoException")
    void validarNumeroUnico_numeroDuplicado_lanzaExcepcion() {
        List<Mesa> existentes = List.of(Mesa.builder().numero(1).build());

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarNumeroUnico(1, existentes));
    }
}
