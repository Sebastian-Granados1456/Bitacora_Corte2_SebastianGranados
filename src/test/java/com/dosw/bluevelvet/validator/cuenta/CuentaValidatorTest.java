package com.dosw.bluevelvet.validator.cuenta;

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
import com.dosw.bluevelvet.model.domain.EstadoCuenta;
import com.dosw.bluevelvet.repository.CuentaRepository;

@ExtendWith(MockitoExtension.class)
class CuentaValidatorTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaValidator validator;

    @Test
    @DisplayName("validarSinCuentaAbierta - mesa sin cuenta abierta no lanza excepcion")
    void validarSinCuentaAbierta_sinCuentaAbierta_noLanza() {
        when(cuentaRepository.existsByMesaIdAndEstado(1L, EstadoCuenta.ABIERTA)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarSinCuentaAbierta(1L));
    }

    @Test
    @DisplayName("validarSinCuentaAbierta - mesa con cuenta abierta lanza RecursoDuplicadoException")
    void validarSinCuentaAbierta_conCuentaAbierta_lanzaExcepcion() {
        when(cuentaRepository.existsByMesaIdAndEstado(1L, EstadoCuenta.ABIERTA)).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarSinCuentaAbierta(1L));
    }
}
