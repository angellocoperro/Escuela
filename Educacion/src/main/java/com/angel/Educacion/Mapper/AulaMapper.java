package com.angel.Educacion.Mapper;

import com.angel.Educacion.Dto.Aulas.AulaRequest;
import com.angel.Educacion.Dto.Aulas.AulaResponse;
import com.angel.Educacion.Entities.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper{

    public Aula requestAEntidad(AulaRequest request){
        if(request == null) return null;

        return Aula.builder()
                .nombre(request.nombre())
                .capacidad(request.capacidad())
                .build();
    }

    public AulaResponse entidadAResponse(Aula aula){
        if(aula == null) return null;

        return new AulaResponse(
          aula.getId(),
          aula.getNombre(),
          aula.getCapacidad()
        );
    }

}// FIN DE LA CLASE AULAMAPPER
