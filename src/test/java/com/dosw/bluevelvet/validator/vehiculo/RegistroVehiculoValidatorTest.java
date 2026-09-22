package com.dosw.bluevelvet.validator.vehiculo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;

class RegistroVehiculoValidatorTest {

    private final RegistroVehiculoValidator validator = new RegistroVehiculoValidator();

    @Test
    @DisplayName("validarSinRegistroActivo - placa sin registro activo no lanza excepcion")
    void validarSinRegistroActivo_sinRegistroActivo_noLanza() {
        assertDoesNotThrow(() -> validator.validarSinRegistroActivo("ABC123", List.of()));
    }

    @Test
    @DisplayName("validarSinRegistroActivo - placa con registro activo lanza RecursoDuplicadoException")
    void validarSinRegistroActivo_conRegistroActivo_lanzaExcepcion() {
        List<RegistroVehiculo> existentes = List.of(
                RegistroVehiculo.builder().placa("ABC123").build());

        assertThrows(RecursoDuplicadoException.class,
                () -> validator.validarSinRegistroActivo("ABC123", existentes));
    }
}
