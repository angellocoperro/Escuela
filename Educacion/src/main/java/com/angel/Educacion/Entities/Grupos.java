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
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint(
        name = "GRUPO_CU_MU_AU_PE_UK",
        columnNames = {"ID_CURSO", "ID_MAESTRO", "ID_AULA", "PERIODO"}))
public class Grupos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestros maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Column(name = "PERIODO", nullable = false, length = 20)
    private String periodo;

    @Builder.Default
    @OneToMany(mappedBy = "grupo")
    private List<Horario> horarios = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "grupo")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public void actualizar(Curso curso, Maestros maestro, Aula aula, String periodo) {
        validarDatos(curso, maestro, aula, periodo);

        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo.trim();
    }

    private void validarDatos(Curso curso, Maestros maestro, Aula aula, String periodo) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso es requerido");
        }
        if (maestro == null) {
            throw new IllegalArgumentException("El maestro es requerido");
        }
        if (aula == null) {
            throw new IllegalArgumentException("El aula es requerida");
        }

        StringCustomUtils.validarTamanio(periodo, 4, 20,
                "El periodo es requerido y debe tener entre 4 y 20 caracteres");
    }

} // FIN DE LA CLASE GRUPO
