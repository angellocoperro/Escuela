package com.angel.Educacion.Entities;

import com.angel.Educacion.Utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "ALUMNOS")
public class Alumnos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "MATRICULA", nullable = false, length = 10)
    private String matricula;

    @Builder.Default
    @Column(name = "FECHA_INGRESO", nullable = false)
    private LocalDate fechaIngreso = LocalDate.now();


    @Builder.Default
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones = new ArrayList<>();


    public boolean cambioEnDatos(String nombre, String apellidoPaterno,
                                 String apellidoMaterno) {
        return !Objects.equals(this.nombre, nombre) ||
                !Objects.equals(this.apellidoPaterno, apellidoPaterno) ||
                !Objects.equals(this.apellidoMaterno, apellidoMaterno);
    }

    public void asignarDatosAcademicos(String email, String matricula) {

        StringCustomUtils.validarTamanio(email,  1, 100,
                 "El email es requerido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(matricula,  10,  10,
                 "La matrícula es requerida y debe tener exactamente 10 caracteres");

        this.email = email.trim().toLowerCase();
        this.matricula = matricula.trim();
    }

    public void actualizar(String nombre, String apellidoPaterno,
                           String apellidoMaterno, String email,
                           String matricula) {
        validarDatos(nombre, apellidoPaterno, apellidoMaterno);

        asignarDatosAcademicos(email, matricula);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
    }

    private void validarDatos(
            String nombre, String apellidoPaterno,
            String apellidoMaterno
    ){
        StringCustomUtils.validarTamanio(nombre.trim(), 4, 50,
                "El nombre es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno.trim(), 4, 50,
                "El apellido paterno es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno.trim(), 4, 50,
                "El apellido materno es requerido y debe tener entre 4 y 50 caracteres");

    }




}
