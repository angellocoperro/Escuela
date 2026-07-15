package com.angel.Educacion.Dto.Aulas;

// @Author:  ANGEL DE JESUS APOLINAR OREGON

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AulaRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "El nombre tiene que tener entre  5 y 30 caracteres")
        String nombre,

        @NotNull(message = "Los capacidad es requerida")
        @Positive(message = "La capacidad tiene que ser positiva")
        Integer capacidad
) {
}
