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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.reserva.ReservaMapperIn;
import com.dosw.bluevelvet.mapper.reserva.ReservaMapperOut;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.validator.ReservaValidator;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    private final ReservaMapperIn mapperIn = new ReservaMapperIn();
    private final ReservaMapperOut mapperOut = new ReservaMapperOut();
    private final ReservaValidator validator = new ReservaValidator();

    @Mock
    private IMesaService mesaService;

    private ReservaServiceImpl reservaService;
    private ReservaRequestDTO requestDto;

    @BeforeEach
    void setUp() {
        reservaService = new ReservaServiceImpl(mapperIn, mapperOut, validator, mesaService);
        requestDto = new ReservaRequestDTO(1L, "Andres Cantor", LocalDateTime.now().plusDays(1), 4);
    }

    @Test
    @DisplayName("Crear reserva sobre mesa existente debe retornar ResponseDTO")
    void crear_mesaExistente_debeRetornarResponseDTO() {
        when(mesaService.buscarPorId(1L)).thenReturn(new MesaResponseDTO(1L, 5, 4, "DISPONIBLE", false));

        ReservaResponseDTO resultado = reservaService.crear(requestDto);

        assertNotNull(resultado.id());
        assertEquals("Andres Cantor", resultado.cliente());
    }

    @Test
    @DisplayName("Crear reserva sobre mesa inexistente debe propagar RecursoNoEncontradoException")
    void crear_mesaInexistente_debePropagarExcepcion() {
        when(mesaService.buscarPorId(1L)).thenThrow(new RecursoNoEncontradoException("Mesa no encontrada: 1"));

        assertThrows(RecursoNoEncontradoException.class, () -> reservaService.crear(requestDto));
    }

    @Test
    @DisplayName("Crear reserva con cruce de horario debe lanzar RecursoDuplicadoException")
    void crear_cruceDeHorario_debeLanzarExcepcion() {
        when(mesaService.buscarPorId(1L)).thenReturn(new MesaResponseDTO(1L, 5, 4, "DISPONIBLE", false));
        reservaService.crear(requestDto);

        assertThrows(RecursoDuplicadoException.class, () -> reservaService.crear(requestDto));
    }
}
