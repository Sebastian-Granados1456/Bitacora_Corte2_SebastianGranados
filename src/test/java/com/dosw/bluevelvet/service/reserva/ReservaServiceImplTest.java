package com.dosw.bluevelvet.service.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.model.domain.Reserva;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.validator.reserva.ReservaValidator;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    private final ReservaValidator validator = new ReservaValidator();

    @Mock
    private IMesaService mesaService;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reservaService = new ReservaServiceImpl(validator, mesaService);
        reserva = Reserva.builder()
                .idMesa(1L).cliente("Andres Cantor")
                .fechaHora(LocalDateTime.now().plusDays(1)).comensales(4).build();
    }

    @Test
    @DisplayName("crear - mesa existente guarda la reserva y le asigna un id")
    void crear_mesaExistente_guardaYRetorna() {
        when(mesaService.obtenerPorId(1L)).thenReturn(Mesa.builder().id(1L).numero(5).capacidad(4).build());

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
        reservaService.crear(reserva);

        Reserva otra = Reserva.builder()
                .idMesa(1L).cliente("Otro Cliente")
                .fechaHora(reserva.getFechaHora()).comensales(2).build();

        assertThrows(RecursoDuplicadoException.class, () -> reservaService.crear(otra));
    }
}
