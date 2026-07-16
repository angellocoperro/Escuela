package com.angel.Educacion.Repository;

import com.angel.Educacion.Entities.Horario;
import com.angel.Educacion.Enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    @Query("""
            SELECT h FROM Horario h
            WHERE h.grupo.id = :idGrupo
            AND h.diaSemana = :dia
            AND (:idExcluir IS NULL OR h.id <> :idExcluir)
            """)
    List<Horario> buscarPorGrupoYDia(
            @Param("idGrupo") Long idGrupo,
            @Param("dia") DiaSemana dia,
            @Param("idExcluir") Long idExcluir);

    @Query("""
            SELECT h FROM Horario h
            WHERE h.grupo.aula.id = :idAula
            AND h.diaSemana = :dia
            AND (:idExcluir IS NULL OR h.id <> :idExcluir)
            """)
    List<Horario> buscarPorAulaYDia(
            @Param("idAula") Long idAula,
            @Param("dia") DiaSemana dia,
            @Param("idExcluir") Long idExcluir);

} // FIN DE LA INTERFACE HORARIOREPOSITORY