package com.angel.Educacion.Dto.Grupos;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosAula;
import com.angel.Educacion.Dto.Datos.DatosCurso;
import com.angel.Educacion.Dto.Datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {
} // FIN DEL RECORD GRUPORESPONSE