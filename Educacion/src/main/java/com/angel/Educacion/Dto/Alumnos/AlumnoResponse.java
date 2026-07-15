package com.angel.Educacion.Dto.Alumnos;

import com.angel.Educacion.Dto.Datos.DatosAlumno;

import java.math.BigDecimal;
import java.util.List;


public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<DatosAlumno> calificaciones,
        BigDecimal promedio
) {
}
