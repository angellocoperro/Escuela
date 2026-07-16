package com.angel.Educacion.Entities;


import com.angel.Educacion.Utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "AULAS")
public class Aula {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Builder.Default
    @OneToMany(mappedBy = "aula")
    private List<Grupos> grupos = new ArrayList<>();

    public void actualizar(String nombre, Integer capacidad){
        validarDatos(nombre, capacidad);
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    private void validarDatos(String nombre, Integer capacidad){
        StringCustomUtils.validarTamanio(nombre, 5, 30,
                "El nombre es requerido y debe tener en 5 y 30 caracteres");
        if(nombre == null ){
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if(capacidad == null){
            throw new IllegalArgumentException("La capacidad es requerida");
        }

    }

} // FIN DE LA CLASE AULA
