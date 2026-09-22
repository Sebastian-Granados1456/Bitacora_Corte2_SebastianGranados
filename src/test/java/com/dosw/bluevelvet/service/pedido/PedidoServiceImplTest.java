package com.dosw.bluevelvet.service.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.dto.pedido.ItemPedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.pedido.PedidoMapperIn;
import com.dosw.bluevelvet.mapper.pedido.PedidoMapperOut;
import com.dosw.bluevelvet.service.plato.IPlatoService;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private IPlatoService platoService;

    private PedidoMapperIn mapperIn;
    private final PedidoMapperOut mapperOut = new PedidoMapperOut();

    private PedidoServiceImpl pedidoService;
    private PedidoRequestDTO requestDto;

    @BeforeEach
    void setUp() {
        mapperIn = new PedidoMapperIn(platoService);
        pedidoService = new PedidoServiceImpl(mapperIn, mapperOut);
        requestDto = new PedidoRequestDTO(1L, List.of(new ItemPedidoRequestDTO(1L, 2)));
    }

    @Test
    @DisplayName("Crear pedido con plato existente debe retornar ResponseDTO con total calculado")
    void crear_platoExistente_debeRetornarResponseDTO() {
        when(platoService.buscarPorId(1L)).thenReturn(new PlatoResponseDTO(1L, "Tacos", 15000.0, "PLATO_FUERTE", true));

        PedidoResponseDTO resultado = pedidoService.crear(requestDto);

        assertNotNull(resultado.id());
        assertEquals("RECIBIDO", resultado.estado());
        assertEquals(30000.0, resultado.total());
    }

    @Test
    @DisplayName("Crear pedido con plato inexistente debe propagar RecursoNoEncontradoException")
    void crear_platoInexistente_debePropagarExcepcion() {
        when(platoService.buscarPorId(1L)).thenThrow(new RecursoNoEncontradoException("Plato no encontrado: 1"));

        assertThrows(RecursoNoEncontradoException.class, () -> pedidoService.crear(requestDto));
    }

    @Test
    @DisplayName("Buscar pedido inexistente debe lanzar RecursoNoEncontradoException")
    void buscarPorId_inexistente_debeLanzarExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> pedidoService.buscarPorId(999L));
    }
}
