package com.angel.Educacion.Service.Aulas;

import com.angel.Educacion.Dto.Aulas.AulaRequest;
import com.angel.Educacion.Dto.Aulas.AulaResponse;
import com.angel.Educacion.Entities.Aula;
import com.angel.Educacion.Entities.Curso;
import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import com.angel.Educacion.Mapper.AulaMapper;
import com.angel.Educacion.Repository.AulaRepository;
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
public class AulaServiceImpl implements AulaService{

    private AulaRepository aularepository;
    private AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar(String nombre, Integer capacidad) {
        log.info("Listando Aula con filtro -> nombre: {}, capacidad: {} ",
                nombre, capacidad);
        Specification<Aula> specification = conFiltro(
          nombre, capacidad
        );
        return aularepository.findAll(specification).stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtnerPorId(long id) {
        Aula aula = obtenerporIdOException(id);
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando una nueva Aula...");
        Aula aula = aulaMapper.requestAEntidad(request);
        aularepository.save(aula);
        log.info("Nueva Aula '{}' registrado con id {}",  aula.getNombre(), aula.getId());

        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(long id, AulaRequest request) {
        log.info("Actualizando una nueva Aula con id: {}", id);
        Aula aula = obtenerporIdOException(id);

        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );
        aularepository.save(aula);
        log.info("Aula con id: {} actualizado", id);
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(long id) {
        log.info("Eliminando Aula con id: {} ", id);
        Aula aula = obtenerporIdOException(id);
        aularepository.delete(aula);
        log.info("Aula por Id: {} eliminado", id);
    }
    private Aula obtenerporIdOException(long id){
        log.info("Obteniendo Aula por id: {}", id);
        return aularepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Aula no encontrado por id: " +id)
        );
    }
    private Specification<Aula> conNombre(String nombre){
        if(nombre == null) return null;
        String patron = "%" + nombre.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), patron);
    }
    private Specification<Aula> conCapacidad(Integer capacidad){
        if(capacidad == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("capacidad"), capacidad);
    }

    private Specification<Aula> conFiltro(
        String nombre, Integer capacidad){

        return Specification.where(conNombre(nombre))
                .and(conCapacidad(capacidad));
    }



}// FIN DE LA CLASE SERVICE
