package com.angel.Educacion.Dto.Grupos;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record GrupoRequest(

        @NotNull(message = "El id del curso es requerido")
        @Positive(message = "El id del curso debe ser positivo")
        Long idCurso,

        @NotNull(message = "El id del maestro es requerido")
        @Positive(message = "El id del maestro debe ser positivo")
        Long idMaestro,

        @NotNull(message = "El id del aula es requerido")
        @Positive(message = "El id del aula debe ser positivo")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        @Size(min = 4, max = 20, message = "El periodo es requerido y debe tener entre 4 y 20 caracteres")
        String periodo

) {
} // FIN DEL RECORD GRUPOREQUEST