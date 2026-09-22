package com.dosw.bluevelvet.service.plato;

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
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.validator.plato.IPlatoValidator;

@ExtendWith(MockitoExtension.class)
class PlatoServiceImplTest {

    @Mock
    private IPlatoValidator validator;

    @InjectMocks
    private PlatoServiceImpl service;

    @Test
    @DisplayName("crear - guarda el plato y le asigna un id")
    void crear_platoCorrecto_guardaYRetorna() {
        Plato entrada = Plato.builder()
                .nombre("Tacos").precio(15000.0).categoria("PLATO_FUERTE").disponible(true).build();

        Plato resultado = service.crear(entrada);

        assertNotNull(resultado.getId());
        assertEquals("Tacos", resultado.getNombre());
        verify(validator, times(1)).validarNombreUnico(eq("Tacos"), any());
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(999L));
    }

    @Test
    @DisplayName("crear - nombre duplicado lanza RecursoDuplicadoException")
    void crear_nombreDuplicado_lanzaConflicto() {
        doThrow(new RecursoDuplicadoException("Nombre duplicado"))
                .when(validator).validarNombreUnico(any(), any());

        Plato plato = Plato.builder().nombre("Tacos").precio(15000.0).categoria("PLATO_FUERTE").build();

        assertThrows(RecursoDuplicadoException.class, () -> service.crear(plato));
    }
}
