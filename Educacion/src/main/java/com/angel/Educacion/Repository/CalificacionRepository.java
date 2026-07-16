package com.angel.Educacion.Repository;

import com.angel.Educacion.Entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    boolean existsByInscripcionId(Long idInscripcion);

    boolean existsByInscripcionIdAndIdNot(Long idInscripcion, Long id);

} // FIN DE LA INTERFACE CALIFICACIONREPOSITORY