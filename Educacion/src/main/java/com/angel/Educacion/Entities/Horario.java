package com.angel.Educacion.Entities;

import com.angel.Educacion.Enums.DiaSemana;
import com.angel.Educacion.Utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "HORARIOS")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupos grupo;

    @Column(name = "DIA", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @Column(name = "HORA_INICIO", nullable = false, length = 5)
    private String horaInicio;

    @Column(name = "HORA_FIN", nullable = false, length = 5)
    private String horaFin;

    public void actualizar(Grupos grupo, DiaSemana diaSemana, String horaInicio, String horaFin) {
        validarDatos(grupo, diaSemana, horaInicio, horaFin);

        this.grupo = grupo;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio.trim();
        this.horaFin = horaFin.trim();
    }

    private void validarDatos(Grupos grupo, DiaSemana diaSemana, String horaInicio, String horaFin) {
        if (grupo == null) {
            throw new IllegalArgumentException("El grupo es requerido");
        }
        if (diaSemana == null) {
            throw new IllegalArgumentException("El dia es requerido");
        }

        StringCustomUtils.validarNoVacio(horaInicio, "La hora de inicio es requerida");
        StringCustomUtils.validarNoVacio(horaFin, "La hora de fin es requerida");

        if (!horaInicio.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
            throw new IllegalArgumentException("La hora de inicio debe tener el formato HH:mm");
        }
        if (!horaFin.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
            throw new IllegalArgumentException("La hora de fin debe tener el formato HH:mm");
        }

        if (!LocalTime.parse(horaFin).isAfter(LocalTime.parse(horaInicio))) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }

} // FIN DE LA CLASE HORARIO