package com.angel.Educacion.Mapper;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosAlumnoResumen;
import com.angel.Educacion.Dto.Datos.DatosGrupo;
import com.angel.Educacion.Dto.Inscripciones.InscripcionRequest;
import com.angel.Educacion.Dto.Inscripciones.InscripcionResponse;
import com.angel.Educacion.Entities.Alumnos;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Inscripcion;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Repository.AlumnoRepository;
import com.angel.Educacion.Repository.GruposRepository;
import com.angel.Educacion.Utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion> {

    private final AlumnoRepository alumnoRepository;
    private final GruposRepository gruposRepository;

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;

        Alumnos alumno = alumnoRepository.findById(request.idAlumno())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Alumno no encontrado por id: " + request.idAlumno()));

        Grupos grupo = gruposRepository.findById(request.idGrupo())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Grupo no encontrado por id: " + request.idGrupo()));

        return Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .build();
    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        Alumnos alumno = entidad.getAlumno();
        Grupos grupo = entidad.getGrupo();

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

        BigDecimal calificacion = entidad.getCalificacion() != null
                ? entidad.getCalificacion().getCalificacion()
                : null;

        return new InscripcionResponse(
                entidad.getId(),
                datosAlumno,
                datosGrupo,
                calificacion,
                StringCustomUtils.localDateAString(entidad.getFechaInscripcion())
        );
    }

} // FIN DE LA CLASE INSCRIPCIONMAPPER