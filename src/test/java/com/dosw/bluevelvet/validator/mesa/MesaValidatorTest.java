package com.dosw.bluevelvet.validator.mesa;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.MesaRepository;

@ExtendWith(MockitoExtension.class)
class MesaValidatorTest {

    @Mock
    private MesaRepository mesaRepository;

    @InjectMocks
    private MesaValidator validator;

    @Test
    @DisplayName("validarNumeroUnico - numero nuevo no lanza excepcion")
    void validarNumeroUnico_numeroNuevo_noLanza() {
        when(mesaRepository.existsByNumero(2)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarNumeroUnico(2));
    }

    @Test
    @DisplayName("validarNumeroUnico - numero duplicado lanza RecursoDuplicadoException")
    void validarNumeroUnico_numeroDuplicado_lanzaExcepcion() {
        when(mesaRepository.existsByNumero(1)).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarNumeroUnico(1));
    }
}
