package com.dosw.bluevelvet.service.vehiculo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.entity.RegistroVehiculoEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.vehiculo.RegistroVehiculoEntityMapper;
import com.dosw.bluevelvet.model.domain.RegistroVehiculo;
import com.dosw.bluevelvet.repository.RegistroVehiculoRepository;
import com.dosw.bluevelvet.validator.vehiculo.IRegistroVehiculoValidator;

@ExtendWith(MockitoExtension.class)
class RegistroVehiculoServiceImplTest {

    @Mock
    private RegistroVehiculoRepository registroVehiculoRepository;

    @Mock
    private RegistroVehiculoEntityMapper entityMapper;

    @Mock
    private IRegistroVehiculoValidator validator;

    @InjectMocks
    private RegistroVehiculoServiceImpl vehiculoService;

    @Test
    @DisplayName("registrarEntrada - placa valida guarda el registro via repository y le asigna un id")
    void registrarEntrada_placaValida_guardaYRetorna() {
        LocalDateTime ahora = LocalDateTime.now();
        RegistroVehiculo registro = RegistroVehiculo.builder().placa("ABC123").entrada(ahora).build();
        RegistroVehiculoEntity entidadSinGuardar = new RegistroVehiculoEntity(null, "ABC123", ahora, null);
        RegistroVehiculoEntity entidadGuardada = new RegistroVehiculoEntity(1L, "ABC123", ahora, null);
        RegistroVehiculo esperado = RegistroVehiculo.builder().id(1L).placa("ABC123").entrada(ahora).build();

        when(entityMapper.toEntity(registro)).thenReturn(entidadSinGuardar);
        when(registroVehiculoRepository.save(entidadSinGuardar)).thenReturn(entidadGuardada);
        when(entityMapper.toDomain(entidadGuardada)).thenReturn(esperado);

        RegistroVehiculo resultado = vehiculoService.registrarEntrada(registro);

        assertNotNull(resultado.getId());
        assertEquals("ABC123", resultado.getPlaca());
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(registroVehiculoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> vehiculoService.obtenerPorId(999L));
    }
}
