package com.dosw.bluevelvet.service.cuenta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.entity.CuentaEntity;
import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.cuenta.CuentaEntityMapper;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.EstadoCuenta;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.repository.CuentaRepository;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.service.pedido.IPedidoService;
import com.dosw.bluevelvet.validator.cuenta.ICuentaValidator;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private CuentaEntityMapper entityMapper;

    @Mock
    private ICuentaValidator validator;

    @Mock
    private IMesaService mesaService;

    @Mock
    private IPedidoService pedidoService;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() {
        lenient().when(mesaService.obtenerPorId(1L)).thenReturn(Mesa.builder().id(1L).numero(5).capacidad(4).build());
        lenient().when(pedidoService.obtenerPorMesa(anyLong())).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("abrir - mesa existente crea la cuenta via repository en estado ABIERTA")
    void abrir_mesaExistente_creaCuenta() {
        LocalDateTime ahora = LocalDateTime.now();
        Cuenta paraGuardar = Cuenta.builder().idMesa(1L).estado(EstadoCuenta.ABIERTA).fechaApertura(ahora).build();
        CuentaEntity entidadSinGuardar = new CuentaEntity(null, new MesaEntity(), EstadoCuenta.ABIERTA, ahora);
        CuentaEntity entidadGuardada = new CuentaEntity(1L, new MesaEntity(), EstadoCuenta.ABIERTA, ahora);
        Cuenta esperado = Cuenta.builder().id(1L).idMesa(1L).estado(EstadoCuenta.ABIERTA).fechaApertura(ahora).build();

        when(entityMapper.toEntity(any(Cuenta.class))).thenReturn(entidadSinGuardar);
        when(cuentaRepository.save(entidadSinGuardar)).thenReturn(entidadGuardada);
        when(entityMapper.toDomain(entidadGuardada)).thenReturn(esperado);

        Cuenta resultado = cuentaService.abrir(1L);

        assertNotNull(resultado.getId());
        assertEquals(EstadoCuenta.ABIERTA, resultado.getEstado());
        assertEquals(0.0, resultado.calcularTotal());
    }

    @Test
    @DisplayName("abrir - mesa con cuenta abierta lanza RecursoDuplicadoException")
    void abrir_mesaConCuentaAbierta_lanzaExcepcion() {
        doThrow(new RecursoDuplicadoException("La mesa 1 ya tiene una cuenta abierta"))
                .when(validator).validarSinCuentaAbierta(1L);

        assertThrows(RecursoDuplicadoException.class, () -> cuentaService.abrir(1L));
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(cuentaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> cuentaService.obtenerPorId(999L));
    }
}
