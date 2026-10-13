package com.dosw.bluevelvet.service.mesa;

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

import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.mesa.MesaEntityMapper;
import com.dosw.bluevelvet.model.domain.EstadoMesa;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.repository.MesaRepository;
import com.dosw.bluevelvet.validator.mesa.IMesaValidator;

@ExtendWith(MockitoExtension.class)
class MesaServiceImplTest {

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private MesaEntityMapper entityMapper;

    @Mock
    private IMesaValidator validator;

    @InjectMocks
    private MesaServiceImpl mesaService;

    @Test
    @DisplayName("crear - guarda la mesa via repository y retorna el dominio con id asignado por la BD")
    void crear_mesaValida_guardaYRetorna() {
        Mesa entrada = Mesa.builder().numero(5).capacidad(4)
                .estado(EstadoMesa.DISPONIBLE).cuentaAbierta(false).build();
        MesaEntity entidadSinGuardar = new MesaEntity(null, 5, 4, EstadoMesa.DISPONIBLE, false);
        MesaEntity entidadGuardada = new MesaEntity(1L, 5, 4, EstadoMesa.DISPONIBLE, false);
        Mesa esperado = Mesa.builder().id(1L).numero(5).capacidad(4)
                .estado(EstadoMesa.DISPONIBLE).cuentaAbierta(false).build();

        when(entityMapper.toEntity(entrada)).thenReturn(entidadSinGuardar);
        when(mesaRepository.save(entidadSinGuardar)).thenReturn(entidadGuardada);
        when(entityMapper.toDomain(entidadGuardada)).thenReturn(esperado);

        Mesa resultado = mesaService.crear(entrada);

        assertNotNull(resultado.getId());
        assertEquals(5, resultado.getNumero());
        verify(validator, times(1)).validarNumeroUnico(5);
    }

    @Test
    @DisplayName("crear - numero duplicado lanza RecursoDuplicadoException")
    void crear_numeroDuplicado_lanzaExcepcion() {
        doThrow(new RecursoDuplicadoException("Numero duplicado"))
                .when(validator).validarNumeroUnico(5);

        Mesa mesa = Mesa.builder().numero(5).capacidad(4).build();

        assertThrows(RecursoDuplicadoException.class, () -> mesaService.crear(mesa));
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(mesaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> mesaService.obtenerPorId(999L));
    }

    @Test
    @DisplayName("eliminar - id inexistente lanza RecursoNoEncontradoException sin llamar deleteById")
    void eliminar_noExiste_lanzaExcepcion() {
        when(mesaRepository.existsById(999L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> mesaService.eliminar(999L));
    }
}
