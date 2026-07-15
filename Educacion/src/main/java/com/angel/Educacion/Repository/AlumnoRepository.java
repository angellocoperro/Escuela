package com.angel.Educacion.Repository;

import com.angel.Educacion.Dto.Alumnos.AlumnoRequest;
import com.angel.Educacion.Entities.Alumnos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlumnoRepository extends JpaRepository<Alumnos, Long> {

    @Query(nativeQuery = true, value = """
            SELECT GENERAR_MATRICULA(:nombre, :paterno, :materno FROM DUAL
    """)
    String generarMatricula(
            @Param("nombre") String nombre,
            @Param("paterno") String apellidoPaterno,
            @Param("Materno") String apellidoMaterno);

    @Query(nativeQuery = true, value = """
        SELECT GENERAR_CORREO(:nombre, :paterno, :materno) FROM DUAL
        """)
    String generarEmail(
            @Param("nombre") String nombre,
            @Param("paterno") String apellidoPaterno,
            @Param("materno") String apellidoMaterno);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMatricula(String matricula);


}
