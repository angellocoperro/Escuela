package com.angel.Educacion.Dto.Alumnos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlumnoRequest(

        @NotBlank(message = "El nombre es requirido")
        @Size(min = 5, max = 50, message =  "El nombre es requerido y debe tener en 4 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El Apellido Paterno es requirido")
        @Size(min = 5, max = 50, message =  "El Apellido es requerido y debe tener en 4 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El Apellido Materno es requirido")
        @Size(min = 5, max = 50, message =  "El Apellido es requerido y debe tener en 4 y 50 caracteres")
        String apellidoMaterno


        /*
        @NotBlank(message = "El Email es requirido")
        @Email (message = "tipo de dato debe ser email")
        @Size(min = 15, max = 100, message =  "El Email es requerido y debe tener en 15 y 50 caracteres")
        String email,


        @NotBlank(message = "La Matricula es requirida")
        @Size(min = 10, max = 10, message =  "El Matricula es requerida y debe tener en 15 y 100 caracteres")
        String matricula


        @NotBlank(message = "La Fecha de ingreso es requirida")
        String fechaIngreso
        */



) {
}
