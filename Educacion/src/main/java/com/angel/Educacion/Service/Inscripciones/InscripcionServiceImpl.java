package com.angel.Educacion.Service.Inscripciones;

import com.angel.Educacion.Dto.Inscripciones.InscripcionRequest;
import com.angel.Educacion.Dto.Inscripciones.InscripcionResponse;
import com.angel.Educacion.Entities.Alumnos;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Inscripcion;
import com.angel.Educacion.Exceptions.EntidadRelacionadaException;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Mapper.InscripcionMapper;
import com.angel.Educacion.Repository.AlumnoRepository;
import com.angel.Educacion.Repository.GruposRepository;
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
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoRepository alumnoRepository;

    private final GruposRepository gruposRepository;

    private final InscripcionMapper inscripcionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {

        log.info("Listando todas las inscripciones");

        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerPorIdOException(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {

        log.info("Registrando nueva inscripcion...");

        validarUnicidad(request.idAlumno(), request.idGrupo(), null);

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request);

        inscripcionRepository.save(inscripcion);

        log.info("Nueva inscripcion registrada con id {}", inscripcion.getId());

        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        Inscripcion inscripcion = obtenerPorIdOException(id);

        log.info("Actualizando inscripcion con id {}", id);

        validarUnicidad(request.idAlumno(), request.idGrupo(), id);

        Alumnos alumno = obtenerAlumnoOException(request.idAlumno());
        Grupos grupo = obtenerGrupoOException(request.idGrupo());

        inscripcion.actualizar(alumno, grupo);

        log.info("Inscripcion {} actualizada correctamente", inscripcion.getId());

        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerPorIdOException(id);

        log.info("Eliminando inscripcion con id: {}", id);

        if (inscripcion.getCalificacion() != null) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar la inscripcion porque ya tiene una calificacion asociada");
        }

        inscripcionRepository.delete(inscripcion);

        log.info("Inscripcion con id {} eliminada", id);
    }

    private Inscripcion obtenerPorIdOException(Long id) {
        log.info("Obteniendo inscripcion por id: {}", id);
        return inscripcionRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Inscripcion no encontrada por id: " + id));
    }

    private Alumnos obtenerAlumnoOException(Long id) {
        log.info("Obteniendo alumno por id: {}", id);
        return alumnoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Alumno no encontrado por id: " + id));
    }

    private Grupos obtenerGrupoOException(Long id) {
        log.info("Obteniendo grupo por id: {}", id);
        return gruposRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Grupo no encontrado por id: " + id));
    }

    private void validarUnicidad(Long idAlumno, Long idGrupo, Long idExcluir) {

        log.info("Validando que el alumno no este ya inscrito en ese grupo...");

        boolean existe = (idExcluir == null)
                ? inscripcionRepository.existsByAlumnoIdAndGrupoId(idAlumno, idGrupo)
                : inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(idAlumno, idGrupo, idExcluir);

        if (existe) {
            throw new IllegalArgumentException("El alumno ya se encuentra inscrito en ese grupo");
        }
    }

} // FIN DE LA CLASE INSCRIPCIONSERVICEIMPL