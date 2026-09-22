package com.dosw.bluevelvet.service.reserva;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dosw.bluevelvet.dto.reserva.ReservaRequestDTO;
import com.dosw.bluevelvet.dto.reserva.ReservaResponseDTO;
import com.dosw.bluevelvet.exception.RecursoNoEncontradoException;
import com.dosw.bluevelvet.mapper.reserva.ReservaMapperIn;
import com.dosw.bluevelvet.mapper.reserva.ReservaMapperOut;
import com.dosw.bluevelvet.model.domain.Reserva;
import com.dosw.bluevelvet.service.mesa.IMesaService;
import com.dosw.bluevelvet.util.IdGenerator;
import com.dosw.bluevelvet.validator.ReservaValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservaServiceImpl implements IReservaService {

    private final Map<Long, Reserva> reservas = new ConcurrentHashMap<>();
    private final AtomicLong contadorId = new AtomicLong(0);

    private final ReservaMapperIn mapperIn;
    private final ReservaMapperOut mapperOut;
    private final ReservaValidator reservaValidator;
    private final IMesaService mesaService;

    @Override
    public List<ReservaResponseDTO> obtenerTodas() {
        return reservas.values().stream()
                .map(mapperOut::toDTO)
                .toList();
    }

    @Override
    public ReservaResponseDTO buscarPorId(Long id) {
        log.debug("Buscando reserva con id={}", id);
        return mapperOut.toDTO(buscarOLanzar(id));
    }

    @Override
    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        log.info("Creando reserva para la mesa {} a nombre de '{}'", dto.idMesa(), dto.cliente());

        // Verifica que la mesa exista (delega en IMesaService, nunca accede
        // directamente a los datos de otro dominio)
        mesaService.buscarPorId(dto.idMesa());
        reservaValidator.validarSinCruceDeHorario(reservas, dto.idMesa(), dto.fechaHora());

        Reserva reserva = mapperIn.toDomain(dto);
        reserva.setId(IdGenerator.siguiente(contadorId));
        reservas.put(reserva.getId(), reserva);

        log.info("Reserva creada con id={} para la mesa {}", reserva.getId(), reserva.getIdMesa());
        return mapperOut.toDTO(reserva);
    }

    @Override
    public void cancelar(Long id) {
        buscarOLanzar(id);
        reservas.remove(id);
        log.info("Reserva id={} cancelada", id);
    }

    private Reserva buscarOLanzar(Long id) {
        return Optional.ofNullable(reservas.get(id))
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada: " + id));
    }
}
