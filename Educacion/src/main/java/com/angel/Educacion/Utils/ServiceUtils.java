package com.angel.Educacion.Utils;

import com.angel.Educacion.Exceptions.RecursoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public class ServiceUtils {

    public static <E, ID> E obtenerEntidadOException(
            JpaRepository<E, ID> repository,
            ID id,
            Class<E> clase
    ){
        String nombreEntidad = clase.getSimpleName();

        log.info("Buscamos {} con id: {} ", nombreEntidad, id);

        return repository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(nombreEntidad + "no encontrado con id: "+id));
    }

}
