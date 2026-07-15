package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Alumnos.AlumnoRequest;
import com.angel.Educacion.Dto.Alumnos.AlumnoResponse;
import com.angel.Educacion.Service.Alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {

    public AlumnoController(AlumnoService service) {
        super(service);
    }

}
