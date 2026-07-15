package com.angel.Educacion.Service.Aulas;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Aulas.AulaRequest;
import com.angel.Educacion.Dto.Aulas.AulaResponse;
import java.util.List;
public interface AulaService {

    List<AulaResponse> listar(
            String nombre, Integer capacidad
    );
    AulaResponse obtnerPorId(long id);
    AulaResponse registrar(AulaRequest request);
    AulaResponse actualizar(long id, AulaRequest request);
    void eliminar(long id);

}// FIN DE LA INTERFACE
