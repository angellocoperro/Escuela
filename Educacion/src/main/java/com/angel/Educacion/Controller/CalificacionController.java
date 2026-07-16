package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Calificaciones.CalificacionRequest;
import com.angel.Educacion.Dto.Calificaciones.CalificacionResponse;
import com.angel.Educacion.Service.Calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {

    public CalificacionController(CalificacionService service) {
        super(service);
    }

}