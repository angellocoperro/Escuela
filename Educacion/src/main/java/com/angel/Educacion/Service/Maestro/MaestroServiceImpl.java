package com.angel.Educacion.Service.Maestro;

import com.angel.Educacion.Dto.Maestros.MaestroRequest;
import com.angel.Educacion.Dto.Maestros.MaestroResponse;
import com.angel.Educacion.Entities.Maestros;
import com.angel.Educacion.Exceptions.EntidadRelacionadaException;
import com.angel.Educacion.Mapper.MaestroMapper;
import com.angel.Educacion.Repository.GruposRepository;
import com.angel.Educacion.Repository.MaestroRepository;
import com.angel.Educacion.Utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class MaestroServiceImpl implements MaestrosService {


    private final MaestroRepository maestroRepository;

    private final MaestroMapper maestroMapper;

    private GruposRepository gruposRepository;




    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {

        log.info("Listando todos los maestros");

        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();

    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo maestro...");

        validarDatosUnicos(request);

        Maestros maestro = maestroMapper.requestAEntidad(request);

        maestroRepository.save(maestro);

        log.info("Nuevo maestro {} registrado", maestro.getNombre());

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        Maestros maestro = obtenerMaestro(id);

        log.info("Actualizando maestro con id {}", id);

        validarCambiosUnicos(request, id);

        maestro.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono()
        );

        log.info("Maestro {} actualizado correctamente ", maestro.getId());

        return maestroMapper.entidadAResponse(maestro);


    }

    @Override
    public void eliminar(Long id) {
        Maestros maestro = obtenerMaestro(id);

        log.info("Eliminando maestro con id: {}", id);

        if(gruposRepository.existsByMaestroId(id)) {
            throw new EntidadRelacionadaException("No se puede eliminar el maestro que ya tiene grupos asignados");
        }

        maestroRepository.delete(maestro);

        log.info("Maestro con id {} eliminado", id);
    }

    private Maestros obtenerMaestro(Long id){
        return ServiceUtils.obtenerEntidadOException(maestroRepository, id, Maestros.class);
    }

    private void validarDatosUnicos(MaestroRequest request) {

        log.info("Validando email único...");

        if (maestroRepository.existsByEmailIgnoreCase(request.email()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());

        log.info("Validando teléfono único...");

        if (maestroRepository.existsByTelefono(request.telefono()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el teléfono: " + request.telefono());
    }

    private void validarCambiosUnicos(MaestroRequest request, Long id) {

        log.info("Validando cambio en email único..");

        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), id))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());

        log.info("Validando cambio en teléfono único..");

        if (maestroRepository.existsByTelefonoAndIdNot(request.telefono(), id))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el teléfono: " + request.telefono());
    }


}
