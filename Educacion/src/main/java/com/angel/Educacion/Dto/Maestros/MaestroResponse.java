package com.angel.Educacion.Dto.Maestros;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosCurso;

import java.util.List;

public record MaestroResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos
) {
} // FIN DEL RECORD MAESTRORESPONSE
