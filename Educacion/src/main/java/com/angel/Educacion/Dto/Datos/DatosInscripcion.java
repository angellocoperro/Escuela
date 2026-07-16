package com.angel.Educacion.Dto.Datos;

public record DatosInscripcion(
        DatosAlumnoResumen alumno,
        DatosGrupo grupo,
        String fechaInscripcion
) {
}
