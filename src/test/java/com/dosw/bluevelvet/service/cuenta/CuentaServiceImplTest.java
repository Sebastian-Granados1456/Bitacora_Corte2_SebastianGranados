package com.dosw.bluevelvet.service.cuenta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Cuenta;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.service.pedido.IPedidoService;
import com.dosw.bluevelvet.validator.cuenta.CuentaValidator;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    private final CuentaValidator validator = new CuentaValidator();

    @Mock
    private IMesaService mesaService;

    @Mock
    private IPedidoService pedidoService;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() {
        cuentaService = new CuentaServiceImpl(validator, mesaService, pedidoService);
        lenient().when(mesaService.obtenerPorId(1L)).thenReturn(Mesa.builder().id(1L).numero(5).capacidad(4).build());
        lenient().when(pedidoService.obtenerPorMesa(anyLong())).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("abrir - mesa existente crea la cuenta en estado ABIERTA con total en 0")
    void abrir_mesaExistente_creaCuenta() {
        Cuenta resultado = cuentaService.abrir(1L);

        assertNotNull(resultado.getId());
        assertEquals("ABIERTA", resultado.getEstado().name());
        assertEquals(0.0, resultado.calcularTotal());
    }

    @Test
    @DisplayName("abrir - mesa con cuenta abierta lanza RecursoDuplicadoException")
    void abrir_mesaConCuentaAbierta_lanzaExcepcion() {
        cuentaService.abrir(1L);

        assertThrows(RecursoDuplicadoException.class, () -> cuentaService.abrir(1L));
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> cuentaService.obtenerPorId(999L));
    }
}
