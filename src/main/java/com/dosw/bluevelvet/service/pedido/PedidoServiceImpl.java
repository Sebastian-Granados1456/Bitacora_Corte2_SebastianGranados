package com.dosw.bluevelvet.service.pedido;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.util.IdGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    @Override
    public List<Pedido> obtenerTodos() {
        return pedidos.values().stream().toList();
    }

    @Override
    public List<Pedido> obtenerPorMesa(Long idMesa) {
        return pedidos.values().stream()
                .filter(p -> p.getIdMesa().equals(idMesa))
                .toList();
    }

    @Override
    public Pedido obtenerPorId(Long id) {
        return pedidos.values().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado: id={}", id);
                    return new RecursoNoEncontradoException("Pedido no encontrado: " + id);
                });
    }

    @Override
    public Pedido crear(Pedido pedido) {
        pedido.setId(IdGenerator.siguiente(contadorId));
        pedidos.put(pedido.getId(), pedido);
        log.info("Pedido id={} creado para la mesa {}", pedido.getId(), pedido.getIdMesa());
        return pedido;
    }
}
