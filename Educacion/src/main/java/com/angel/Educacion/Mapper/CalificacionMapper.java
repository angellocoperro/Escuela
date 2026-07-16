package com.angel.Educacion.Mapper;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Calificaciones.CalificacionRequest;
import com.angel.Educacion.Dto.Calificaciones.CalificacionResponse;
import com.angel.Educacion.Dto.Datos.DatosAlumnoResumen;
import com.angel.Educacion.Dto.Datos.DatosGrupo;
import com.angel.Educacion.Dto.Datos.DatosInscripcion;
import com.angel.Educacion.Entities.Alumnos;
import com.angel.Educacion.Entities.Calificacion;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Inscripcion;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Repository.InscripcionRepository;
import com.angel.Educacion.Utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {

    private final InscripcionRepository inscripcionRepository;

    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if (request == null) return null;

        Inscripcion inscripcion = inscripcionRepository.findById(request.idInscripcion())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Inscripcion no encontrada por id: " + request.idInscripcion()));

        return Calificacion.builder()
                .inscripcion(inscripcion)
                .calificacion(request.calificacion())
                .fechaRegistro(LocalDate.now())
                .build();
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;

        Inscripcion inscripcion = entidad.getInscripcion();
        Alumnos alumno = inscripcion.getAlumno();
        Grupos grupo = inscripcion.getGrupo();

        DatosAlumnoResumen datosAlumno = new DatosAlumnoResumen(
                String.join(" ", alumno.getNombre(), alumno.getApellidoPaterno(), alumno.getApellidoMaterno()),
                alumno.getMatricula(),
                alumno.getEmail(),
                StringCustomUtils.localDateAString(alumno.getFechaIngreso())
        );

        DatosGrupo datosGrupo = new DatosGrupo(
                grupo.getCurso().getNombre(),
                String.join(" ",
                        grupo.getMaestro().getNombre(),
                        grupo.getMaestro().getApellidoPaterno(),
                        grupo.getMaestro().getApellidoMaterno()),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );

        DatosInscripcion datosInscripcion = new DatosInscripcion(
                datosAlumno,
                datosGrupo,
                StringCustomUtils.localDateAString(inscripcion.getFechaInscripcion())
        );

        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                StringCustomUtils.localDateAString(entidad.getFechaRegistro())
        );
    }

} // FIN DE LA CLASE CALIFICACIONMAPPER