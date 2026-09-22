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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.dto.cuenta.CuentaRequestDTO;
import com.dosw.bluevelvet.dto.cuenta.CuentaResponseDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.exception.RecursoDuplicadoException;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.cuenta.CuentaMapperOut;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.service.pedido.IPedidoService;
import com.dosw.bluevelvet.validator.CuentaValidator;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    private final CuentaMapperOut mapperOut = new CuentaMapperOut();
    private final CuentaValidator validator = new CuentaValidator();

    @Mock
    private IMesaService mesaService;

    @Mock
    private IPedidoService pedidoService;

    private CuentaServiceImpl cuentaService;
    private CuentaRequestDTO requestDto;

    @BeforeEach
    void setUp() {
        cuentaService = new CuentaServiceImpl(mapperOut, validator, mesaService, pedidoService);
        requestDto = new CuentaRequestDTO(1L);

        lenient().when(mesaService.buscarPorId(1L)).thenReturn(new MesaResponseDTO(1L, 5, 4, "DISPONIBLE", false));
        lenient().when(pedidoService.obtenerPorMesa(anyLong())).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("Abrir cuenta sobre mesa existente debe retornar ResponseDTO en estado ABIERTA")
    void abrir_mesaExistente_debeRetornarResponseDTO() {
        CuentaResponseDTO resultado = cuentaService.abrir(requestDto);

        assertNotNull(resultado.id());
        assertEquals("ABIERTA", resultado.estado());
        assertEquals(0.0, resultado.total());
    }

    @Test
    @DisplayName("Abrir cuenta cuando la mesa ya tiene una abierta debe lanzar RecursoDuplicadoException")
    void abrir_mesaConCuentaAbierta_debeLanzarExcepcion() {
        cuentaService.abrir(requestDto);

        assertThrows(RecursoDuplicadoException.class, () -> cuentaService.abrir(requestDto));
    }

    @Test
    @DisplayName("Buscar cuenta inexistente debe lanzar RecursoNoEncontradoException")
    void buscarPorId_inexistente_debeLanzarExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> cuentaService.buscarPorId(999L));
    }
}
