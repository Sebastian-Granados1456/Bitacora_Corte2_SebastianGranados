package com.dosw.bluevelvet.validator.plato;

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
import com.dosw.bluevelvet.repository.PlatoRepository;

@ExtendWith(MockitoExtension.class)
class PlatoValidatorTest {

    @Mock
    private PlatoRepository platoRepository;

    @InjectMocks
    private PlatoValidator validator;

    @Test
    @DisplayName("validarNombreUnico - nombre nuevo no lanza excepcion")
    void validarNombreUnico_nombreNuevo_noLanza() {
        when(platoRepository.existsByNombreIgnoreCase("Bandeja Paisa")).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarNombreUnico("Bandeja Paisa"));
    }

    @Test
    @DisplayName("validarNombreUnico - nombre duplicado lanza RecursoDuplicadoException")
    void validarNombreUnico_nombreDuplicado_lanzaExcepcion() {
        when(platoRepository.existsByNombreIgnoreCase("Ajiaco")).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarNombreUnico("Ajiaco"));
    }
}
