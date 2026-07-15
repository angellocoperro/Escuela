package com.angel.Educacion.Dto.Maestros;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MaestroRequest(

        @NotBlank(message = "El nombre es requirido")
        @Size(min = 5, max = 50, message =  "El nombre es requerido y debe tener en 4 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El Apellido Paterno es requirido")
        @Size(min = 5, max = 50, message =  "El Apellido es requerido y debe tener en 4 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El Apellido Materno es requirido")
        @Size(min = 5, max = 50, message =  "El Apellido es requerido y debe tener en 4 y 50 caracteres")
        String apellidoMaterno,

        @NotBlank(message = "El Email es requirido")
        @Size(min = 15, max = 100, message =  "El Email es requerido y debe tener en 15 y 50 caracteres")
        String email,

        @NotBlank(message = "El numero de telefono es requirido")
        @Size(min = 10, max = 10, message =  "El Telefono es requerido y debe tener 10 caracteres")
        String telefono
) {
}
