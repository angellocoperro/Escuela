package com.angel.Educacion.Controller;


import com.angel.Educacion.Dto.Aulas.AulaRequest;
import com.angel.Educacion.Dto.Aulas.AulaResponse;
import com.angel.Educacion.Dto.Cursos.CursoRequest;
import com.angel.Educacion.Dto.Cursos.CursoResponse;
import com.angel.Educacion.Service.Aulas.AulaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/aula")
@AllArgsConstructor
@Validated
public class AulaController {

    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer capacidad
    ) {

         return ResponseEntity.ok(aulaService.listar(
                 nombre, capacidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponse> obtenerPorId(
        @PathVariable @Positive(message = "El Id debe de ser positivo") Long id) {
        return ResponseEntity.ok(aulaService.obtnerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AulaResponse> registrar(
            @Valid @RequestBody AulaRequest request){
        AulaResponse aula = aulaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(aula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponse> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody AulaRequest request){

        return ResponseEntity.ok(aulaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El id tiene que ser positivo") Long id){
        aulaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }




} // FIN DE LA CLASE AULACONTROLLER
