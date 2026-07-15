package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Maestros.MaestroRequest;
import com.angel.Educacion.Dto.Maestros.MaestroResponse;
import com.angel.Educacion.Service.Maestro.MaestrosService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/maestros")
public class MaestroController extends CommonController <MaestroRequest, MaestroResponse, MaestrosService>{

    public MaestroController(MaestrosService service) {
        super(service);
    }

}
