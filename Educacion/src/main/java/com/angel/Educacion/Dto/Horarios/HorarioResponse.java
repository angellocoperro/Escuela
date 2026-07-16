package com.angel.Educacion.Dto.Horarios;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo grupo,
        String horario
) {
} // FIN DEL RECORD HORARIORESPONSE