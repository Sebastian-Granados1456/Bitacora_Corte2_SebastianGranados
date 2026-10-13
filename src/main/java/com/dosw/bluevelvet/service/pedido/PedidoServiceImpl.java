package com.dosw.bluevelvet.service.pedido;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.entity.ItemPedidoEntity;
import com.dosw.bluevelvet.entity.PedidoEntity;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.pedido.PedidoEntityMapper;
import com.dosw.bluevelvet.model.domain.ItemPedido;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoEntityMapper entityMapper;

    @Override
    public List<Pedido> obtenerTodos() {
        return entityMapper.toDomainList(pedidoRepository.findAll());
    }

    @Override
    public List<Pedido> obtenerPorMesa(Long idMesa) {
        return entityMapper.toDomainList(pedidoRepository.findByMesaId(idMesa));
    }

    @Override
    public Pedido obtenerPorId(Long id) {
        PedidoEntity entidad = buscarEntidadOLanzar(id);
        return entityMapper.toDomain(entidad);
    }

    @Override
    public Pedido crear(Pedido pedido) {
        PedidoEntity entidad = entityMapper.toEntity(pedido);

        List<ItemPedidoEntity> items = pedido.getItems().stream()
                .map(entityMapper::toEntity)
                .peek(itemEntity -> itemEntity.setPedido(entidad))
                .toList();
        entidad.setItems(items);

        PedidoEntity guardada = pedidoRepository.save(entidad);

        log.info("Pedido id={} creado para la mesa {}", guardada.getId(), pedido.getIdMesa());
        return entityMapper.toDomain(guardada);
    }

    private PedidoEntity buscarEntidadOLanzar(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Pedido no encontrado: " + id);
                });
    }
}
