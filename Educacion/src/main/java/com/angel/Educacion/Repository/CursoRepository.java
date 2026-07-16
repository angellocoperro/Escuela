package com.angel.Educacion.Repository;

import com.angel.Educacion.Entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>,
        JpaSpecificationExecutor<Curso> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

} // FIN DE LA INTERFACE CURSOREPOSITORY
