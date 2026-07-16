package com.angel.Educacion.Repository;

import com.angel.Educacion.Entities.Grupos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GruposRepository extends JpaRepository<Grupos, Long> {

    boolean existsByMaestroId(long idMaestro);

    boolean existsByCursoId(long idCurso);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoIgnoreCase(
            Long idCurso, Long idMaestro, Long idAula, String periodo);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoIgnoreCaseAndIdNot(
            Long idCurso, Long idMaestro, Long idAula, String periodo, Long id);

}
