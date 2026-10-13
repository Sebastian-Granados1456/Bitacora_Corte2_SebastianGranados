package com.dosw.bluevelvet.service.pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dosw.bluevelvet.entity.ItemPedidoEntity;
import com.dosw.bluevelvet.entity.MesaEntity;
import com.dosw.bluevelvet.entity.PedidoEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.pedido.PedidoEntityMapper;
import com.dosw.bluevelvet.model.domain.EstadoPedido;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoEntityMapper entityMapper;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .idMesa(1L)
                .estado(EstadoPedido.RECIBIDO)
                .timestamp(LocalDateTime.now())
                .items(List.of(ItemPedido.builder()
                        .idPlato(1L).nombrePlato("Tacos").precioCongelado(15000.0).cantidad(2).build()))
                .build();
    }

    @Test
    @DisplayName("crear - guarda el pedido via repository y le asigna un id")
    void crear_pedidoValido_guardaYRetorna() {
        PedidoEntity entidadSinGuardar = new PedidoEntity(null, new MesaEntity(), EstadoPedido.RECIBIDO, pedido.getTimestamp(), null);
        PedidoEntity entidadGuardada = new PedidoEntity(1L, new MesaEntity(), EstadoPedido.RECIBIDO, pedido.getTimestamp(), List.of());
        ItemPedidoEntity itemEntity = new ItemPedidoEntity(null, null, 1L, "Tacos", 15000.0, 2);
        Pedido esperado = Pedido.builder().id(1L).idMesa(1L).estado(EstadoPedido.RECIBIDO)
                .timestamp(pedido.getTimestamp()).items(pedido.getItems()).build();

        when(entityMapper.toEntity(pedido)).thenReturn(entidadSinGuardar);
        when(entityMapper.toEntity(pedido.getItems().get(0))).thenReturn(itemEntity);
        when(pedidoRepository.save(entidadSinGuardar)).thenReturn(entidadGuardada);
        when(entityMapper.toDomain(entidadGuardada)).thenReturn(esperado);

        Pedido resultado = pedidoService.crear(pedido);

        assertNotNull(resultado.getId());
        assertEquals(EstadoPedido.RECIBIDO, resultado.getEstado());
        assertEquals(30000.0, resultado.calcularTotal());
    }

    @Test
    @DisplayName("obtenerPorId - id inexistente lanza RecursoNoEncontradoException")
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> pedidoService.obtenerPorId(999L));
    }

    @Test
    @DisplayName("obtenerPorMesa - delega en el repository con el idMesa correcto")
    void obtenerPorMesa_delegaEnRepository() {
        when(pedidoRepository.findByMesaId(1L)).thenReturn(List.of());
        when(entityMapper.toDomainList(any())).thenReturn(List.of());

        List<Pedido> resultado = pedidoService.obtenerPorMesa(1L);

        assertEquals(0, resultado.size());
    }
}
