package com.angel.Educacion.Dto.Inscripciones;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosAlumnoResumen;
import com.angel.Educacion.Dto.Datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumnoResumen alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {
} // FIN DEL RECORD INSCRIPCIONRESPONSE