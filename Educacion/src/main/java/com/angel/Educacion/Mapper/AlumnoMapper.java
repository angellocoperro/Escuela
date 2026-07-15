package com.angel.Educacion.Mapper;

import com.angel.Educacion.Dto.Alumnos.AlumnoRequest;
import com.angel.Educacion.Dto.Alumnos.AlumnoResponse;
import com.angel.Educacion.Dto.Datos.DatosAlumno;
import com.angel.Educacion.Entities.Alumnos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumnos>{

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Alumnos requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;

        return Alumnos.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    } // FIN DEL METODO REQUESTAENTIDAD

    @Override
    public AlumnoResponse entidadAResponse(Alumnos entidad) {
        if (entidad == null) return null;

        List<DatosAlumno> calificaciones = entidadADatosAlumno(entidad);

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                entidad.getFechaIngreso().format(FORMATO_FECHA),
                calificaciones,
                calcularPromedio(calificaciones)
        );
    } // FIN DEL METODO ENTIDADARESPONSE

    private List<DatosAlumno> entidadADatosAlumno(Alumnos entidad) {
        if (entidad == null) return List.of();

        return entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosAlumno(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null
                                ? inscripcion.getCalificacion().getCalificacion()
                                : null
                ))
                .toList();
    } // FIN DEL METODO ENTIDADADATOSALUMNO

    private BigDecimal calcularPromedio(List<DatosAlumno> calificaciones) {
        List<BigDecimal> calificacionesValidas = calificaciones.stream()
                .map(DatosAlumno::calificacion)
                .filter(Objects::nonNull)
                .toList();

        if (calificacionesValidas.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal suma = calificacionesValidas.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return suma.divide(BigDecimal.valueOf(calificacionesValidas.size()), 2, RoundingMode.HALF_UP);
    } // FIN DEL METODO CALCULARPROMEDIO

}
