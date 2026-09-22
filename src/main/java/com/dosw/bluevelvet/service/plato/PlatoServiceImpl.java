package com.dosw.bluevelvet.service.plato;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.dto.plato.PlatoRequestDTO;
import com.dosw.bluevelvet.dto.plato.PlatoResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.plato.PlatoMapperIn;
import com.dosw.bluevelvet.mapper.plato.PlatoMapperOut;
import com.dosw.bluevelvet.model.domain.Plato;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.PlatoValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatoServiceImpl implements IPlatoService {

    private final Map<Long, Plato> platos = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final PlatoMapperIn mapperIn;
    private final PlatoMapperOut mapperOut;
    private final PlatoValidator platoValidator;

    @Override
    public List<PlatoResponseDTO> obtenerTodos() {
        return platos.values().stream()
                .map(mapperOut::toDTO)
                .toList();
    }

    @Override
    public PlatoResponseDTO buscarPorId(Long id) {
        log.debug("Buscando plato con id={}", id);
        return mapperOut.toDTO(buscarOLanzar(id));
    }

    @Override
    public PlatoResponseDTO crear(PlatoRequestDTO dto) {
        log.info("Creando plato con nombre '{}'", dto.nombre());
        platoValidator.validarNombreUnico(platos, dto.nombre());

        Plato plato = mapperIn.toDomain(dto);
        plato.setId(IdGenerator.siguiente(contadorId));
        platos.put(plato.getId(), plato);

        log.info("Plato '{}' creado con id={}", plato.getNombre(), plato.getId());
        return mapperOut.toDTO(plato);
    }

    @Override
    public void eliminar(Long id) {
        buscarOLanzar(id);
        platos.remove(id);
        log.info("Plato id={} eliminado", id);
    }

    private Plato buscarOLanzar(Long id) {
        return Optional.ofNullable(platos.get(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato no encontrado: " + id));
    }
}
