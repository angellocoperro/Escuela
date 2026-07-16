package com.angel.Educacion.Dto.Inscripciones;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InscripcionRequest(

        @NotNull(message = "El id del alumno es requerido")
        @Positive(message = "El id del alumno debe ser positivo")
        Long idAlumno,

        @NotNull(message = "El id del grupo es requerido")
        @Positive(message = "El id del grupo debe ser positivo")
        Long idGrupo

) {
} // FIN DEL RECORD INSCRIPCIONREQUEST