package com.angel.Educacion.Repository;

import com.angel.Educacion.Entities.Grupos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GruposRepository extends JpaRepository<Grupos, Long> {

    boolean existsByMaestroId(long idMaestro);
}
