package com.dosw.bluevelvet.service.mesa;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.dto.mesa.MesaRequestDTO;
import com.dosw.bluevelvet.dto.mesa.MesaResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.mesa.MesaMapperIn;
import com.dosw.bluevelvet.mapper.mesa.MesaMapperOut;
import com.dosw.bluevelvet.model.domain.Mesa;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.MesaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MesaServiceImpl implements IMesaService {

    private final Map<Long, Mesa> mesas = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final MesaMapperIn mapperIn;
    private final MesaMapperOut mapperOut;
    private final MesaValidator mesaValidator;

    @Override
    public List<MesaResponseDTO> obtenerTodas() {
        return mesas.values().stream()
                .map(mapperOut::toDTO)
                .toList();
    }

    @Override
    public MesaResponseDTO buscarPorId(Long id) {
        log.debug("Buscando mesa con id={}", id);
        return mapperOut.toDTO(buscarOLanzar(id));
    }

    @Override
    public MesaResponseDTO crear(MesaRequestDTO dto) {
        log.info("Creando mesa numero {}", dto.numero());
        mesaValidator.validarNumeroUnico(mesas, dto.numero());

        Mesa mesa = mapperIn.toDomain(dto);
        mesa.setId(IdGenerator.siguiente(contadorId));
        mesas.put(mesa.getId(), mesa);

        log.info("Mesa numero {} creada con id={}", mesa.getNumero(), mesa.getId());
        return mapperOut.toDTO(mesa);
    }

    @Override
    public void marcarCuentaAbierta(Long id, boolean abierta) {
        Mesa mesa = buscarOLanzar(id);
        mesa.setCuentaAbierta(abierta);
        log.info("Mesa id={} cuentaAbierta={}", id, abierta);
    }

    @Override
    public void eliminar(Long id) {
        buscarOLanzar(id);
        mesas.remove(id);
        log.info("Mesa id={} eliminada", id);
    }

    private Mesa buscarOLanzar(Long id) {
        return Optional.ofNullable(mesas.get(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Mesa no encontrada: " + id));
    }
}
