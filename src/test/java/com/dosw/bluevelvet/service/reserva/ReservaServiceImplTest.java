package com.dosw.bluevelvet.service.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.entity.ReservaEntity;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.reserva.ReservaEntityMapper;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.model.domain.Reserva;
import com.dosw.bluevelvet.repository.ReservaRepository;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.validator.reserva.IReservaValidator;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaEntityMapper entityMapper;

    @Mock
    private IReservaValidator validator;

    @Mock
    private IMesaService mesaService;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = Reserva.builder()
                .idMesa(1L).cliente("Andres Cantor")
                .fechaHora(LocalDateTime.now().plusDays(1)).comensales(4).build();
    }

    @Test
    @DisplayName("crear - mesa existente guarda la reserva via repository y le asigna un id")
    void crear_mesaExistente_guardaYRetorna() {
        when(mesaService.obtenerPorId(1L)).thenReturn(Mesa.builder().id(1L).numero(5).capacidad(4).build());

        ReservaEntity entidadSinGuardar = new ReservaEntity(null, new MesaEntity(), "Andres Cantor", reserva.getFechaHora(), 4);
        ReservaEntity entidadGuardada = new ReservaEntity(1L, new MesaEntity(), "Andres Cantor", reserva.getFechaHora(), 4);
        Reserva esperado = Reserva.builder().id(1L).idMesa(1L).cliente("Andres Cantor")
                .fechaHora(reserva.getFechaHora()).comensales(4).build();

        when(entityMapper.toEntity(reserva)).thenReturn(entidadSinGuardar);
        when(reservaRepository.save(entidadSinGuardar)).thenReturn(entidadGuardada);
        when(entityMapper.toDomain(entidadGuardada)).thenReturn(esperado);

        Reserva resultado = reservaService.crear(reserva);

        assertNotNull(resultado.getId());
        assertEquals("Andres Cantor", resultado.getCliente());
    }

    @Test
    @DisplayName("crear - mesa inexistente propaga RecursoNoEncontradoException")
    void crear_mesaInexistente_propagaExcepcion() {
        when(mesaService.obtenerPorId(1L)).thenThrow(new RecursoNoEncontradoException("Mesa no encontrada: 1"));

        assertThrows(RecursoNoEncontradoException.class, () -> reservaService.crear(reserva));
    }

    @Test
    @DisplayName("crear - cruce de horario lanza RecursoDuplicadoException")
    void crear_cruceDeHorario_lanzaExcepcion() {
        when(mesaService.obtenerPorId(1L)).thenReturn(Mesa.builder().id(1L).numero(5).capacidad(4).build());
        doThrow(new RecursoDuplicadoException("Ya existe una reserva"))
                .when(validator).validarSinCruceDeHorario(1L, reserva.getFechaHora());

        assertThrows(RecursoDuplicadoException.class, () -> reservaService.crear(reserva));
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(reservaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> reservaService.obtenerPorId(999L));
    }

    @Test
    @DisplayName("cancelar - id inexistente lanza RecursoNoEncontradoException sin llamar deleteById")
    void cancelar_noExiste_lanzaExcepcion() {
        when(reservaRepository.existsById(999L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> reservaService.cancelar(999L));
    }
}
