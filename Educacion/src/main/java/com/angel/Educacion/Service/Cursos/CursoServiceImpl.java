package com.angel.Educacion.Service.Cursos;

import com.angel.Educacion.Exceptions.EntidadRelacionadaException;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Dto.Cursos.CursoRequest;
import com.angel.Educacion.Dto.Cursos.CursoResponse;
import com.angel.Educacion.Entities.Curso;
import com.angel.Educacion.Mapper.CursoMapper;
import com.angel.Educacion.Repository.CursoRepository;
import com.angel.Educacion.Repository.GruposRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private GruposRepository gruposRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar(
            String nombre, String descripcion,
            Integer creditos) {
        log.info("Listando Cursos con filtro  -> nombre: {}, descripcion: {}, creditos: {}   ",
                nombre, descripcion, creditos);

        Specification<Curso> specification = conFiltro(
                nombre, descripcion, creditos
        );
        return cursoRepository.findAll(specification).stream()
                .map(cursoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(long id) {
        Curso curso = obtenerPorIdOException(id);
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrando un nuevo Curso...");

        validarNombreUnico(request.nombre(), null);

        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);
        log.info("Nuevo Curso '{}' registrado con id {}",  curso.getNombre(), curso.getId());

        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(long id, CursoRequest request) {
        log.info("Actualizando un nuevo Curso con id: {}", id);
        Curso curso = obtenerPorIdOException(id);

        validarNombreUnico(request.nombre(), id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );
        cursoRepository.save(curso);
        log.info("Curso con id: {} actualizado", id);
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(long id) {
        log.info("Eliminando Curso con id: {} ", id);
        Curso curso = obtenerPorIdOException(id);

        if(gruposRepository.existsByCursoId(id)){
            throw new EntidadRelacionadaException("No se puede eliminar el Curso que ya tiene grupos asignados");
        }

        cursoRepository.delete(curso);
        log.info("Curso por Id: {} eliminado", id);
    }

    private Curso obtenerPorIdOException(long id) {
        log.info("Obteniendo Curso por id: {}", id);
        return cursoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Curso no encontrado por id: " +id));
    }

    private void validarNombreUnico(String nombre, Long idExcluir) {

        log.info("Validando que el nombre del curso sea único...");

        boolean existe = (idExcluir == null)
                ? cursoRepository.existsByNombreIgnoreCase(nombre.trim())
                : cursoRepository.existsByNombreIgnoreCaseAndIdNot(nombre.trim(), idExcluir);

        if (existe) {
            throw new IllegalArgumentException(
                    "Ya existe un curso registrado con el nombre: " + nombre);
        }
    }

    private Specification<Curso> conNombre(String nombre){
        if(nombre == null) return null;
        String patron = "%" + nombre.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), patron);
    }
    private  Specification<Curso> conDescripcion(String descripcion){
        if(descripcion == null) return null;
        String patron = "%" + descripcion.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.equal(root.get("descripcion"), patron);
    }
    private Specification<Curso> conCreditos(Integer creditos){
        if(creditos == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("creditos"), creditos);
    }

    private Specification<Curso> conFiltro(
            String nombre, String descripcion,  Integer creditos
    ){
        return Specification.where(conNombre(nombre))
                .and(conDescripcion(descripcion))
                .and(conCreditos(creditos));
    }
}
