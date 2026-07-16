package com.angel.Educacion.Service.Grupos;

import com.angel.Educacion.Dto.Grupos.GrupoRequest;
import com.angel.Educacion.Dto.Grupos.GrupoResponse;
import com.angel.Educacion.Entities.Aula;
import com.angel.Educacion.Entities.Curso;
import com.angel.Educacion.Entities.Grupos;
import com.angel.Educacion.Entities.Maestros;
import com.angel.Educacion.Exceptions.EntidadRelacionadaException;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Mapper.GrupoMapper;
import com.angel.Educacion.Repository.GruposRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class GrupoServiceImpl implements GrupoService {

    private final GruposRepository gruposRepository;

    private final GrupoMapper grupoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {

        log.info("Listando todos los grupos");

        return gruposRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerPorIdOException(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {

        log.info("Registrando nuevo grupo...");

        validarCombinacionUnica(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo(), null);

        Grupos grupo = grupoMapper.requestAEntidad(request);

        gruposRepository.save(grupo);

        log.info("Nuevo grupo registrado con id {}", grupo.getId());

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Grupos grupo = obtenerPorIdOException(id);

        log.info("Actualizando grupo con id {}", id);

        validarCombinacionUnica(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo(), id);

        Curso curso = grupoMapper.obtenerCursoOException(request.idCurso());
        Maestros maestro = grupoMapper.obtenerMaestroOException(request.idMaestro());
        Aula aula = grupoMapper.obtenerAulaOException(request.idAula());

        grupo.actualizar(curso, maestro, aula, request.periodo());

        log.info("Grupo {} actualizado correctamente", grupo.getId());

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupos grupo = obtenerPorIdOException(id);

        log.info("Eliminando grupo con id: {}", id);

        if (!grupo.getInscripciones().isEmpty() || !grupo.getHorarios().isEmpty()) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el grupo porque ya tiene inscripciones u horarios asociados");
        }

        gruposRepository.delete(grupo);

        log.info("Grupo con id {} eliminado", id);
    }

    private Grupos obtenerPorIdOException(Long id) {
        log.info("Obteniendo grupo por id: {}", id);
        return gruposRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Grupo no encontrado por id: " + id));
    }

    private void validarCombinacionUnica(Long idCurso, Long idMaestro, Long idAula, String periodo, Long idExcluir) {

        log.info("Validando combinacion Curso+Maestro+Aula+Periodo unica...");

        boolean existe = (idExcluir == null)
                ? gruposRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoIgnoreCase(
                idCurso, idMaestro, idAula, periodo.trim())
                : gruposRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoIgnoreCaseAndIdNot(
                idCurso, idMaestro, idAula, periodo.trim(), idExcluir);

        if (existe) {
            throw new IllegalArgumentException(
                    "Ya existe un grupo registrado con esa combinacion de curso, maestro, aula y periodo");
        }
    }

} // FIN DE LA CLASE GRUPOSERVICEIMPL