package com.angel.Educacion.Dto.Datos;

import java.math.BigDecimal;

public record DatosAlumno(
        String curso,
        String periodo,
        BigDecimal calificacion
) {
}
