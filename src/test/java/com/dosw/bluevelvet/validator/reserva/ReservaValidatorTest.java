package com.dosw.bluevelvet.validator.reserva;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ReservaValidatorTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaValidator validator;

    @Test
    @DisplayName("validarSinCruceDeHorario - horario libre no lanza excepcion")
    void validarSinCruceDeHorario_horarioLibre_noLanza() {
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        when(reservaRepository.existsByMesaIdAndFechaHora(1L, fecha)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarSinCruceDeHorario(1L, fecha));
    }

    @Test
    @DisplayName("validarSinCruceDeHorario - horario ocupado lanza RecursoDuplicadoException")
    void validarSinCruceDeHorario_horarioOcupado_lanzaExcepcion() {
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        when(reservaRepository.existsByMesaIdAndFechaHora(1L, fecha)).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> validator.validarSinCruceDeHorario(1L, fecha));
    }
}
