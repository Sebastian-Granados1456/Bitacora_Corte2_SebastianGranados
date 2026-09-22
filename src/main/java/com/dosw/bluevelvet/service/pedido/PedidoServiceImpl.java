package com.dosw.bluevelvet.service.pedido;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.pedido.PedidoMapperIn;
import com.dosw.bluevelvet.mapper.pedido.PedidoMapperOut;
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

    private final PedidoMapperIn mapperIn;
    private final PedidoMapperOut mapperOut;

    @Override
    public List<PedidoResponseDTO> obtenerTodos() {
        return pedidos.values().stream()
                .map(mapperOut::toDTO)
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> obtenerPorMesa(Long idMesa) {
        return pedidos.values().stream()
                .filter(p -> p.getIdMesa().equals(idMesa))
                .map(mapperOut::toDTO)
                .toList();
    }

    @Override
    public PedidoResponseDTO buscarPorId(Long id) {
        log.debug("Buscando pedido con id={}", id);
        return mapperOut.toDTO(buscarOLanzar(id));
    }

    @Override
    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        log.info("Creando pedido para la mesa {}", dto.idMesa());

        Pedido pedido = mapperIn.toDomain(dto);
        pedido.setId(IdGenerator.siguiente(contadorId));
        pedidos.put(pedido.getId(), pedido);

        log.info("Pedido id={} creado para la mesa {}", pedido.getId(), pedido.getIdMesa());
        return mapperOut.toDTO(pedido);
    }

    private Pedido buscarOLanzar(Long id) {
        return Optional.ofNullable(pedidos.get(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado: " + id));
    }
}
