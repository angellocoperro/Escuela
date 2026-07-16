package com.angel.Educacion.Mapper;

import com.angel.Educacion.Dto.Datos.DatosCurso;
import com.angel.Educacion.Dto.Maestros.MaestroRequest;
import com.angel.Educacion.Dto.Maestros.MaestroResponse;
import com.angel.Educacion.Entities.Maestros;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestros> {

    private final CursoMapper cursoMapper;

    @Override
    public Maestros requestAEntidad(MaestroRequest request) {
        if(request == null) return null;

        return Maestros.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim().toLowerCase())
                .telefono(request.telefono().trim())
                .build();
    }

    @Override
    public MaestroResponse entidadAResponse(Maestros entidad) {
        if(entidad == null) return null;

        List<DatosCurso> cursos = entidadADatosCurso(entidad);

        return new MaestroResponse(
          entidad.getId(),
          String.join(" ",
                  entidad.getNombre(),
                  entidad.getApellidoPaterno(),
                  entidad.getApellidoMaterno()),
          entidad.getEmail(),
          entidad.getTelefono(),
          cursos
        );

    }

    private List<DatosCurso> entidadADatosCurso(Maestros entidad){
        if(entidad==null) return List.of();

        return entidad.getGrupos().stream()
                .map(grupo -> cursoMapper.entidadADatosCurso(grupo.getCurso())).toList();

    }

}
