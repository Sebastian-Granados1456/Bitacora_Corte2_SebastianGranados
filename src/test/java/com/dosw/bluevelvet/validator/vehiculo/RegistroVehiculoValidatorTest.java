package com.dosw.bluevelvet.validator.vehiculo;

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
import com.dosw.bluevelvet.repository.RegistroVehiculoRepository;

@ExtendWith(MockitoExtension.class)
class RegistroVehiculoValidatorTest {

    @Mock
    private RegistroVehiculoRepository registroVehiculoRepository;

    @InjectMocks
    private RegistroVehiculoValidator validator;

    @Test
    @DisplayName("validarSinRegistroActivo - placa sin registro activo no lanza excepcion")
    void validarSinRegistroActivo_sinRegistroActivo_noLanza() {
        when(registroVehiculoRepository.existsByPlacaIgnoreCaseAndSalidaIsNull("ABC123")).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarSinRegistroActivo("ABC123"));
    }

    @Test
    @DisplayName("validarSinRegistroActivo - placa con registro activo lanza RecursoDuplicadoException")
    void validarSinRegistroActivo_conRegistroActivo_lanzaExcepcion() {
        when(registroVehiculoRepository.existsByPlacaIgnoreCaseAndSalidaIsNull("ABC123")).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarSinRegistroActivo("ABC123"));
    }
}
