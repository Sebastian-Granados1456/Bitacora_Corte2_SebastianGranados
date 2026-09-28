package com.dosw.bluevelvet.service.plato;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.entity.PlatoEntity;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.plato.PlatoEntityMapper;
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.repository.PlatoRepository;
import com.dosw.bluevelvet.validator.plato.IPlatoValidator;

@ExtendWith(MockitoExtension.class)
class PlatoServiceImplTest {

    @Mock
    private PlatoRepository platoRepository;

    @Mock
    private PlatoEntityMapper entityMapper;

    @Mock
    private IPlatoValidator validator;

    @InjectMocks
    private PlatoServiceImpl service;

    @Test
    @DisplayName("crear - guarda el plato via repository y retorna el dominio con id asignado por la BD")
    void crear_platoCorrecto_guardaYRetorna() {
        Plato entrada = Plato.builder().nombre("Tacos").precio(15000.0).categoria("PLATO_FUERTE").build();
        PlatoEntity entidadSinGuardar = new PlatoEntity(null, "Tacos", 15000.0, "PLATO_FUERTE", null);
        PlatoEntity entidadGuardada = new PlatoEntity(1L, "Tacos", 15000.0, "PLATO_FUERTE", null);
        Plato esperado = Plato.builder().id(1L).nombre("Tacos").precio(15000.0).categoria("PLATO_FUERTE").build();

        when(entityMapper.toEntity(entrada)).thenReturn(entidadSinGuardar);
        when(platoRepository.save(entidadSinGuardar)).thenReturn(entidadGuardada);
        when(entityMapper.toDomain(entidadGuardada)).thenReturn(esperado);

        Plato resultado = service.crear(entrada);

        assertNotNull(resultado.getId());
        assertEquals("Tacos", resultado.getNombre());
        verify(validator, times(1)).validarNombreUnico("Tacos");
    }

    @Test
    @DisplayName("crear - nombre duplicado lanza RecursoDuplicadoException y no llega a guardar")
    void crear_nombreDuplicado_lanzaConflicto() {
        doThrow(new RecursoDuplicadoException("Nombre duplicado"))
                .when(validator).validarNombreUnico("Tacos");

        Plato plato = Plato.builder().nombre("Tacos").precio(15000.0).categoria("PLATO_FUERTE").build();

        assertThrows(RecursoDuplicadoException.class, () -> service.crear(plato));
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(platoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(999L));
    }

    @Test
    @DisplayName("eliminar - id inexistente lanza RecursoNoEncontradoException sin llamar deleteById")
    void eliminar_noExiste_lanzaExcepcion() {
        when(platoRepository.existsById(999L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> service.eliminar(999L));
    }
}
