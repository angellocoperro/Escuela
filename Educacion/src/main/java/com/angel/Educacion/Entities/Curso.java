package com.angel.Educacion.Entities;


import ch.qos.logback.core.util.StringUtil;
import com.angel.Educacion.Utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "CURSOS")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    @Builder.Default
    @OneToMany(mappedBy = "curso")
    private List<Grupos> grupos = new ArrayList<>();


    public void actualizar(String nombre, String descripcion, Integer creditos) {
        validarDatos(nombre, descripcion, creditos);

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

    private void validarDatos(String nombre, String descripcion, Integer creditos) {
        StringCustomUtils.validarTamanio(nombre, 5, 100,
                "El nombre es requerido y debe tener en 10 y 100 caracteres");

        StringCustomUtils.validarTamanio(descripcion, 5, 100,
                "El nombre es requerido y debe tener en 10 y 100 caracteres");

        if(nombre == null){
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (descripcion != null) {
            StringCustomUtils.validarTamanio(descripcion, 10, 200,
                    "La descripcion, si se proporciona, debe tener entre 10 y 200 caracteres");
        }
        if(creditos == null){
            throw new IllegalArgumentException("Los creditos son requeridos");
        }
    }

} // FIN DE LA CLASS CURSOS
