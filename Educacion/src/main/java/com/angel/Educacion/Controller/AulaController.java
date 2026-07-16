package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Aulas.AulaRequest;
import com.angel.Educacion.Dto.Aulas.AulaResponse;
import com.angel.Educacion.Service.Aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/aula")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {

    public AulaController(AulaService service) {
        super(service);
    }

}
