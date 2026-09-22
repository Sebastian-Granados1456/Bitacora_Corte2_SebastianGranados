package com.dosw.bluevelvet.service.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.EstadoPedido;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;

// PedidoServiceImpl no tiene dependencias (el congelado de precio ya lo hizo
// el Mapper antes de llegar aqui) — se prueba sin mocks.
@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    private PedidoServiceImpl pedidoService;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedidoService = new PedidoServiceImpl();
        pedido = Pedido.builder()
                .idMesa(1L)
                .estado(EstadoPedido.RECIBIDO)
                .timestamp(LocalDateTime.now())
                .items(List.of(ItemPedido.builder()
                        .idPlato(1L).nombrePlato("Tacos").precioCongelado(15000.0).cantidad(2).build()))
                .build();
    }

    @Test
    @DisplayName("crear - guarda el pedido y le asigna un id")
    void crear_pedidoValido_guardaYRetorna() {
        Pedido resultado = pedidoService.crear(pedido);

        assertNotNull(resultado.getId());
        assertEquals(EstadoPedido.RECIBIDO, resultado.getEstado());
        assertEquals(30000.0, resultado.calcularTotal());
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class, () -> pedidoService.obtenerPorId(999L));
    }

    @Test
    @DisplayName("obtenerPorMesa - filtra correctamente por mesa")
    void obtenerPorMesa_debeFiltrarCorrectamente() {
        pedidoService.crear(pedido);
        pedidoService.crear(Pedido.builder()
                .idMesa(2L).estado(EstadoPedido.RECIBIDO).timestamp(LocalDateTime.now())
                .items(List.of()).build());

        List<Pedido> resultado = pedidoService.obtenerPorMesa(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdMesa());
    }
}
