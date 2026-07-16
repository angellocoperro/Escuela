package com.angel.Educacion.Repository;

import com.angel.Educacion.Entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AulaRepository  extends JpaRepository<Aula, Long>,
        JpaSpecificationExecutor<Aula>
{

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

} // FIN DE LA CLASE AULAREPOSITORY
