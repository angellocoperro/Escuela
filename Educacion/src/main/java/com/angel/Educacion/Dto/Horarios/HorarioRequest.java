package com.angel.Educacion.Dto.Horarios;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record HorarioRequest(

        @NotNull(message = "El id del grupo es requerido")
        @Positive(message = "El id del grupo debe ser positivo")
        Long idGrupo,

        @NotBlank(message = "El dia es requerido")
        String dia,

        @NotBlank(message = "La hora de inicio es requerida")
        @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "La hora de inicio debe tener el formato HH:mm")
        String horaInicio,

        @NotBlank(message = "La hora de fin es requerida")
        @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "La hora de fin debe tener el formato HH:mm")
        String horaFin

) {
} // FIN DEL RECORD HORARIOREQUEST