package com.angel.Educacion.Service.Alumnos;

import com.angel.Educacion.Dto.Alumnos.AlumnoRequest;
import com.angel.Educacion.Dto.Alumnos.AlumnoResponse;
import com.angel.Educacion.Entities.Alumnos;
import com.angel.Educacion.Exceptions.EntidadRelacionadaException;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Mapper.AlumnoMapper;
import com.angel.Educacion.Repository.AlumnoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {

        log.info("Listando todos los alumnos");

        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerPorIdOException(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {

        log.info("Registrando nuevo alumno...");

        Alumnos borrador = alumnoMapper.requestAEntidad(request);

        String matricula = alumnoRepository.generarMatricula(
                borrador.getNombre(), borrador.getApellidoPaterno(), borrador.getApellidoMaterno());

        String email = alumnoRepository.generarEmail(
                borrador.getNombre(), borrador.getApellidoPaterno(), borrador.getApellidoMaterno());

        Alumnos alumno = Alumnos.builder()
                .nombre(borrador.getNombre())
                .apellidoPaterno(borrador.getApellidoPaterno())
                .apellidoMaterno(borrador.getApellidoMaterno())
                .matricula(matricula)
                .email(email)
                .build();

        alumnoRepository.save(alumno);

        log.info("Nuevo alumno {} registrado con matricula {}", alumno.getNombre(), matricula);

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumnos alumno = obtenerPorIdOException(id);

        log.info("Actualizando alumno con id {}", id);

        boolean cambiaronDatosPersonales = alumno.cambioEnDatos(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());

        String matricula = alumno.getMatricula();
        String email = alumno.getEmail();

        if (cambiaronDatosPersonales) {

            log.info("Los datos personales cambiaron, regenerando matricula y email...");

            matricula = alumnoRepository.generarMatricula(
                    request.nombre(), request.apellidoPaterno(), request.apellidoMaterno());

            email = alumnoRepository.generarEmail(
                    request.nombre(), request.apellidoPaterno(), request.apellidoMaterno());
        }

        alumno.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                email,
                matricula
        );

        log.info("Alumno {} actualizado correctamente", alumno.getId());

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumnos alumno = obtenerPorIdOException(id);

        log.info("Eliminando alumno con id: {}", id);

        if (!alumno.getInscripciones().isEmpty()) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el alumno porque ya tiene inscripciones asociadas");
        }

        alumnoRepository.delete(alumno);

        log.info("Alumno con id {} eliminado", id);
    }

    private Alumnos obtenerPorIdOException(Long id) {
        log.info("Obteniendo alumno por id: {}", id);
        return alumnoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Alumno no encontrado por id: " + id));
    }

} // FIN DE LA CLASE ALUMNOSERVICEIMPL