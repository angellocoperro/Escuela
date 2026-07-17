package com.angel.Educacion.Dto.Cursos;

// @Author: ANGEL DE JESUS APOLINAR OREGOM

import jakarta.validation.constraints.*;

public record CursoRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 100, message =  "El nombre es requerido y debe tener en 5 y 100 caracteres")
        String nombre,

        //@NotBlank(message = "La descripcion es requerida")
        @Size(min = 10, max = 200, message =  "La descripcion, si se proporciona, debe tener entre 10 y 200 caracteres")
        String descripcion,

        @NotNull(message = "Los creditos son requeridos")
        @Min(value = 1, message = "Los creditos minimo son 1")
        @Max(value = 10, message = "Los creditos maximos son 10")
        Integer creditos

) {
}
