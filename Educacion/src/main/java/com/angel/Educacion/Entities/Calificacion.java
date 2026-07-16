package com.angel.Educacion.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "CALIFICACIONES")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "CALIFICACION", nullable = false, precision = 3, scale = 1)
    private BigDecimal calificacion;

    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro;



    public void actualizar(BigDecimal calificacion) {
        validarCalificacion(calificacion);
        this.calificacion = calificacion;
    }

    public void reasignarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripcion es requerida");
        }
        this.inscripcion = inscripcion;
    }

    private void validarCalificacion(BigDecimal calificacion) {
        if (calificacion == null) {
            throw new IllegalArgumentException("La calificacion es requerida");
        }
        if (calificacion.compareTo(BigDecimal.ZERO) < 0 || calificacion.compareTo(BigDecimal.TEN) > 0) {
            throw new IllegalArgumentException("La calificacion debe estar entre 0 y 10");
        }
    }

} // FIN DE LA CLASE CALIFICACION
