package com.dosw.bluevelvet.service.mesa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.EstadoMesa;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.validator.mesa.IMesaValidator;

@ExtendWith(MockitoExtension.class)
class MesaServiceImplTest {

    @Mock
    private IMesaValidator validator;

    @InjectMocks
    private MesaServiceImpl mesaService;

    @Test
    @DisplayName("crear - guarda la mesa y le asigna un id")
    void crear_mesaValida_guardaYRetorna() {
        Mesa entrada = Mesa.builder().numero(5).capacidad(4)
                .estado(EstadoMesa.DISPONIBLE).cuentaAbierta(false).build();

        Mesa resultado = mesaService.crear(entrada);

        assertNotNull(resultado.getId());
        assertEquals(5, resultado.getNumero());
        verify(validator, times(1)).validarNumeroUnico(eq(5), any());
    }

    @Test
    @DisplayName("crear - numero duplicado lanza RecursoDuplicadoException")
    void crear_numeroDuplicado_lanzaExcepcion() {
        doThrow(new RecursoDuplicadoException("Numero duplicado"))
                .when(validator).validarNumeroUnico(any(), any());

        Mesa mesa = Mesa.builder().numero(5).capacidad(4).build();

        assertThrows(RecursoDuplicadoException.class, () -> mesaService.crear(mesa));
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> mesaService.obtenerPorId(999L));
    }
}
