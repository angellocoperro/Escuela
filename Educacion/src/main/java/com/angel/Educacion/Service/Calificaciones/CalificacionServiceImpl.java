package com.angel.Educacion.Service.Calificaciones;

import com.angel.Educacion.Dto.Calificaciones.CalificacionRequest;
import com.angel.Educacion.Dto.Calificaciones.CalificacionResponse;
import com.angel.Educacion.Entities.Calificacion;
import com.angel.Educacion.Entities.Inscripcion;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Mapper.CalificacionMapper;
import com.angel.Educacion.Repository.CalificacionRepository;
import com.angel.Educacion.Repository.InscripcionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;

    private final InscripcionRepository inscripcionRepository;

    private final CalificacionMapper calificacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listar() {

        log.info("Listando todas las calificaciones");

        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerPorIdOException(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {

        log.info("Registrando nueva calificacion...");

        validarUnaCalificacionPorInscripcion(request.idInscripcion(), null);

        Calificacion calificacion = calificacionMapper.requestAEntidad(request);

        calificacionRepository.save(calificacion);

        log.info("Nueva calificacion registrada con id {}", calificacion.getId());

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        Calificacion calificacion = obtenerPorIdOException(id);

        log.info("Actualizando calificacion con id {}", id);

        boolean cambioInscripcion = !calificacion.getInscripcion().getId().equals(request.idInscripcion());

        if (cambioInscripcion) {
            validarUnaCalificacionPorInscripcion(request.idInscripcion(), id);

            Inscripcion inscripcion = obtenerInscripcionOException(request.idInscripcion());

            calificacion.reasignarInscripcion(inscripcion);
        }

        calificacion.actualizar(request.calificacion());

        calificacionRepository.save(calificacion);

        log.info("Calificacion {} actualizada correctamente", calificacion.getId());

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerPorIdOException(id);

        log.info("Eliminando calificacion con id: {}", id);

        calificacionRepository.delete(calificacion);

        log.info("Calificacion con id {} eliminada", id);
    }

    private Calificacion obtenerPorIdOException(Long id) {
        log.info("Obteniendo calificacion por id: {}", id);
        return calificacionRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Calificacion no encontrada por id: " + id));
    }

    private Inscripcion obtenerInscripcionOException(Long id) {
        log.info("Obteniendo inscripcion por id: {}", id);
        return inscripcionRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Inscripcion no encontrada por id: " + id));
    }

    private void validarUnaCalificacionPorInscripcion(Long idInscripcion, Long idExcluir) {

        log.info("Validando que la inscripcion no tenga ya una calificacion...");

        boolean existe = (idExcluir == null)
                ? calificacionRepository.existsByInscripcionId(idInscripcion)
                : calificacionRepository.existsByInscripcionIdAndIdNot(idInscripcion, idExcluir);

        if (existe) {
            throw new IllegalArgumentException("Esa inscripcion ya tiene una calificacion registrada");
        }
    }

} // FIN DE LA CLASE CALIFICACIONSERVICEIMPL