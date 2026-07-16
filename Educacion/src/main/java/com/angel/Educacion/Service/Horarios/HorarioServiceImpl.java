package com.angel.Educacion.Service.Horarios;

import com.angel.Educacion.Dto.Horarios.HorarioRequest;
import com.angel.Educacion.Dto.Horarios.HorarioResponse;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Horario;
import com.angel.Educacion.Enums.DiaSemana;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Mapper.HorarioMapper;
import com.angel.Educacion.Repository.GruposRepository;
import com.angel.Educacion.Repository.HorarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;

    private final GruposRepository gruposRepository;

    private final HorarioMapper horarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {

        log.info("Listando todos los horarios");

        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerPorIdOException(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {

        log.info("Registrando nuevo horario...");

        Horario horario = horarioMapper.requestAEntidad(request);

        validarCoherenciaHoras(horario.getHoraInicio(), horario.getHoraFin());
        validarSinTraslape(horario.getGrupo(), horario.getDiaSemana(),
                horario.getHoraInicio(), horario.getHoraFin(), null);

        horarioRepository.save(horario);

        log.info("Nuevo horario registrado con id {}", horario.getId());

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerPorIdOException(id);

        log.info("Actualizando horario con id {}", id);

        Grupos grupo = obtenerGrupoOException(request.idGrupo());
        DiaSemana dia = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

        validarCoherenciaHoras(request.horaInicio(), request.horaFin());
        validarSinTraslape(grupo, dia, request.horaInicio(), request.horaFin(), id);

        horario.actualizar(grupo, dia, request.horaInicio(), request.horaFin());

        log.info("Horario {} actualizado correctamente", horario.getId());

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerPorIdOException(id);

        log.info("Eliminando horario con id: {}", id);

        horarioRepository.delete(horario);

        log.info("Horario con id {} eliminado", id);
    }

    private Horario obtenerPorIdOException(Long id) {
        log.info("Obteniendo horario por id: {}", id);
        return horarioRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Horario no encontrado por id: " + id));
    }

    private Grupos obtenerGrupoOException(Long id) {
        log.info("Obteniendo grupo por id: {}", id);
        return gruposRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Grupo no encontrado por id: " + id));
    }

    private void validarCoherenciaHoras(String horaInicio, String horaFin) {

        log.info("Validando coherencia de horas...");

        if (!horaInicio.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
            throw new IllegalArgumentException("La hora de inicio debe tener el formato HH:mm");
        }
        if (!horaFin.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
            throw new IllegalArgumentException("La hora de fin debe tener el formato HH:mm");
        }
        if (!LocalTime.parse(horaFin).isAfter(LocalTime.parse(horaInicio))) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }

    private void validarSinTraslape(Grupos grupo, DiaSemana dia, String horaInicio, String horaFin, Long idExcluir) {

        log.info("Validando que el horario no se traslape...");

        LocalTime inicioNuevo = LocalTime.parse(horaInicio);
        LocalTime finNuevo = LocalTime.parse(horaFin);

        List<Horario> horariosGrupo = horarioRepository.buscarPorGrupoYDia(grupo.getId(), dia, idExcluir);
        List<Horario> horariosAula = horarioRepository.buscarPorAulaYDia(grupo.getAula().getId(), dia, idExcluir);

        Stream.concat(horariosGrupo.stream(), horariosAula.stream())
                .forEach(existente -> {

                    LocalTime inicioExistente = LocalTime.parse(existente.getHoraInicio());
                    LocalTime finExistente = LocalTime.parse(existente.getHoraFin());

                    boolean seTraslapan = inicioNuevo.isBefore(finExistente) && inicioExistente.isBefore(finNuevo);

                    if (seTraslapan) {
                        throw new IllegalArgumentException(
                                "El horario se traslapa con otro horario ya registrado para el mismo grupo o aula");
                    }
                });
    }

} // FIN DE LA CLASE HORARIOSERVICEIMPL