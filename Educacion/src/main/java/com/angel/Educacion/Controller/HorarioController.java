package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Horarios.HorarioRequest;
import com.angel.Educacion.Dto.Horarios.HorarioResponse;
import com.angel.Educacion.Service.Horarios.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService> {

    public HorarioController(HorarioService service) {
        super(service);
    }

}