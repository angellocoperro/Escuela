package com.angel.Educacion.Mapper;

// @Author: ANGEL DE JESUS APOLINAR OREGON

import com.angel.Educacion.Dto.Datos.DatosAula;
import com.angel.Educacion.Dto.Datos.DatosMaestro;
import com.angel.Educacion.Dto.Grupos.GrupoRequest;
import com.angel.Educacion.Dto.Grupos.GrupoResponse;
import com.angel.Educacion.Entities.Aula;
import com.angel.Educacion.Entities.Curso;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Maestros;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Repository.AulaRepository;
import com.angel.Educacion.Repository.CursoRepository;
import com.angel.Educacion.Repository.MaestroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupos> {

    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final CursoMapper cursoMapper;

    @Override
    public Grupos requestAEntidad(GrupoRequest request) {
        if (request == null) return null;

        Curso curso = obtenerCursoOException(request.idCurso());
        Maestros maestro = obtenerMaestroOException(request.idMaestro());
        Aula aula = obtenerAulaOException(request.idAula());

        return Grupos.builder()
                .curso(curso)
                .maestro(maestro)
                .aula(aula)
                .periodo(request.periodo().trim())
                .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupos entidad) {
        if (entidad == null) return null;

        DatosMaestro datosMaestro = new DatosMaestro(
                String.join(" ",
                        entidad.getMaestro().getNombre(),
                        entidad.getMaestro().getApellidoPaterno(),
                        entidad.getMaestro().getApellidoMaterno()),
                entidad.getMaestro().getEmail(),
                entidad.getMaestro().getTelefono()
        );

        DatosAula datosAula = new DatosAula(
                entidad.getAula().getNombre(),
                entidad.getAula().getCapacidad()
        );

        List<String> horarios = entidad.getHorarios().stream()
                .map(horario -> String.join(" ",
                        horario.getDiaSemana().getDescripcion(),
                        horario.getHoraInicio(),
                        "-",
                        horario.getHoraFin()))
                .toList();

        return new GrupoResponse(
                entidad.getId(),
                cursoMapper.entidadADatosCurso(entidad.getCurso()),
                datosMaestro,
                datosAula,
                horarios,
                entidad.getPeriodo()
        );
    }

    public Curso obtenerCursoOException(Long id) {
        return cursoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Curso no encontrado por id: " + id));
    }

    public Maestros obtenerMaestroOException(Long id) {
        return maestroRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Maestro no encontrado por id: " + id));
    }

    public Aula obtenerAulaOException(Long id) {
        return aulaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Aula no encontrada por id: " + id));
    }

} // FIN DE LA CLASE GRUPOMAPPER