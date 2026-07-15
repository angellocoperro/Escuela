package com.angel.Educacion.Controller;


import com.angel.Educacion.Dto.Cursos.CursoRequest;
import com.angel.Educacion.Dto.Cursos.CursoResponse;
import com.angel.Educacion.Service.Cursos.CursoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/curso")
@AllArgsConstructor
@Validated
public class CursoController {

    private final CursoService cursoService;


    @GetMapping
    public ResponseEntity<List<CursoResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Integer creditos

    ){
        return ResponseEntity.ok(cursoService.listar(
                nombre, descripcion, creditos
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> obtenerPorId(
            @PathVariable @Positive(message = "El Id debe de ser positivo") Long id){
        return ResponseEntity.ok(cursoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CursoResponse> registrar(
        @Valid @RequestBody CursoRequest request){
        CursoResponse curso = cursoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody CursoRequest request){

        return ResponseEntity.ok(cursoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
        @PathVariable @Positive(message = "El id tiene que ser positivo") Long id){
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }





}// FIN DE LA CLASECURSOCONTROLLER
