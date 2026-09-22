package com.dosw.bluevelvet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosw.bluevelvet.controller.docs.PedidoApi;
import com.dosw.bluevelvet.dto.pedido.PedidoRequestDTO;
import com.dosw.bluevelvet.dto.pedido.PedidoResponseDTO;
import com.dosw.bluevelvet.mapper.pedido.PedidoMapper;
import com.dosw.bluevelvet.model.domain.Pedido;
import com.dosw.bluevelvet.service.pedido.IPedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoApi {

    private final IPedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoMapper.toResponseList(pedidoService.obtenerTodos()));
    }

    @Override
    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPorMesa(@PathVariable Long idMesa) {
        return ResponseEntity.ok(pedidoMapper.toResponseList(pedidoService.obtenerPorMesa(idMesa)));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        return ResponseEntity.ok(pedidoMapper.toResponse(pedido));
    }

    @Override
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@RequestBody @Valid PedidoRequestDTO dto) {
        Pedido pedido = pedidoMapper.toDomain(dto);
        Pedido creado = pedidoService.crear(pedido);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoMapper.toResponse(creado));
    }
}
