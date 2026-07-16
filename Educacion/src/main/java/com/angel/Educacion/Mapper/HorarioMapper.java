package com.angel.Educacion.Mapper;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosGrupo;
import com.angel.Educacion.Dto.Horarios.HorarioRequest;
import com.angel.Educacion.Dto.Horarios.HorarioResponse;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Horario;
import com.angel.Educacion.Enums.DiaSemana;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Repository.GruposRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    private final GruposRepository gruposRepository;

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;

        Grupos grupo = gruposRepository.findById(request.idGrupo())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Grupo no encontrado por id: " + request.idGrupo()));

        DiaSemana dia = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

        return Horario.builder()
                .grupo(grupo)
                .diaSemana(dia)
                .horaInicio(request.horaInicio().trim())
                .horaFin(request.horaFin().trim())
                .build();
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        if (entidad == null) return null;

        Grupos grupo = entidad.getGrupo();

        DatosGrupo datosGrupo = new DatosGrupo(
                grupo.getCurso().getNombre(),
                String.join(" ",
                        grupo.getMaestro().getNombre(),
                        grupo.getMaestro().getApellidoPaterno(),
                        grupo.getMaestro().getApellidoMaterno()),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );

        String horario = String.join(" ",
                entidad.getDiaSemana().getDescripcion(),
                entidad.getHoraInicio(),
                entidad.getHoraFin());

        return new HorarioResponse(entidad.getId(), datosGrupo, horario);
    }

} // FIN DE LA CLASE HORARIOMAPPER