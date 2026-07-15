package com.angel.Educacion.Mapper;
// @Author: Angel de Jesus Apolinar Oregon


import com.angel.Educacion.Dto.Cursos.CursoRequest;
import com.angel.Educacion.Dto.Cursos.CursoResponse;
import com.angel.Educacion.Dto.Datos.DatosCurso;
import com.angel.Educacion.Entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso>{

    // Preparar datos para guardar en la base de datos
    public Curso requestAEntidad(CursoRequest request){
        if(request == null) return null;

        String descripcion = request.descripcion() != null
                ? request.descripcion().trim() : null;

        return Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    } // FIN DEL METODO REQUESTAENTIDAD


    // Preparar datos para enviar al cliente
    public CursoResponse entidadAResponse(Curso entidad){
        if(entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null
               ? "Sin descripcion" : entidad.getDescripcion();

        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }

    public DatosCurso entidadADatosCurso(Curso entidad){
        if(entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripcion" : entidad.getDescripcion();

        return new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }

}// FIN DE LA CLASE PRODCUTOR MAPPER
