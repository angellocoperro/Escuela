package com.angel.Educacion.Controller;

import com.angel.Educacion.Dto.Grupos.GrupoRequest;
import com.angel.Educacion.Dto.Grupos.GrupoResponse;
import com.angel.Educacion.Service.Grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {

    public GrupoController(GrupoService service) {
        super(service);
    }

}