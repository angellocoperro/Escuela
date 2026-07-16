package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Inscripciones.InscripcionRequest;
import com.angel.Educacion.Dto.Inscripciones.InscripcionResponse;
import com.angel.Educacion.Service.Inscripciones.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService> {

    public InscripcionController(InscripcionService service) {
        super(service);
    }

}