package com.angel.Educacion.Dto.Calificaciones;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CalificacionRequest(

        @NotNull(message = "El id de la inscripcion es requerido")
        @Positive(message = "El id de la inscripcion debe ser positivo")
        Long idInscripcion,

        @NotNull(message = "La calificacion es requerida")
        @DecimalMin(value = "0.0", message = "La calificacion minima es 0")
        @DecimalMax(value = "10.0", message = "La calificacion maxima es 10")
        BigDecimal calificacion

) {
} // FIN DEL RECORD CALIFICACIONREQUEST