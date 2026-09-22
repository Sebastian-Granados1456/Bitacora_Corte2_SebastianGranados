package com.dosw.bluevelvet.service.vehiculo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;
import com.dosw.bluevelvet.validator.vehiculo.IRegistroVehiculoValidator;

@ExtendWith(MockitoExtension.class)
class RegistroVehiculoServiceImplTest {

    @Mock
    private IRegistroVehiculoValidator validator;

    @InjectMocks
    private RegistroVehiculoServiceImpl vehiculoService;

    @Test
    @DisplayName("registrarEntrada - placa valida guarda el registro y le asigna un id")
    void registrarEntrada_placaValida_guardaYRetorna() {
        RegistroVehiculo registro = RegistroVehiculo.builder()
                .placa("ABC123").entrada(LocalDateTime.now()).build();

        RegistroVehiculo resultado = vehiculoService.registrarEntrada(registro);

        assertNotNull(resultado.getId());
        assertEquals("ABC123", resultado.getPlaca());
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> vehiculoService.obtenerPorId(999L));
    }
}
