package com.angel.Educacion.Service.Cursos;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import java.util.List;

import com.angel.Educacion.Dto.Cursos.CursoRequest;
import com.angel.Educacion.Dto.Cursos.CursoResponse;

public interface CursoService {
    List<CursoResponse> listar(
            String nombre, String descripcion,
            Integer creditos
    );

    CursoResponse obtenerPorId(long id);
    CursoResponse registrar(CursoRequest request);
    CursoResponse actualizar(long id,  CursoRequest request);
    void eliminar(long id);


} // FIN DE LA CLASE CURSOSERVICE
