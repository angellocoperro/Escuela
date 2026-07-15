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
@Table(name = "MAESTROS")
public class Maestros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "TELEFONO",  nullable = false, length = 10)
    private String telefono;

    @Builder.Default
    @OneToMany(mappedBy = "maestro")
    private List<Grupos> grupos = new ArrayList<>();





    public void actualizar(String nombre, String apellidoPaterno,
                           String apellidoMaterno, String email, String telefono) {
        validarDatos(nombre, apellidoPaterno, apellidoMaterno,email,telefono );
        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.toLowerCase().trim();
        this.telefono = telefono.trim();
    }


    private void validarDatos(
            String nombre, String apellidoPaterno,
            String apellidoMaterno, String email, String telefono) {

        StringCustomUtils.validarTamanio(nombre.trim(), 4, 50,
                "El nombre es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno.trim(), 4, 50,
                "El apellido paterno es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno.trim(), 4, 50,
                "El apellido materno es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(email.trim(), 8, 100,
                "El email es requerido y debe tener entre 8 y 100 caracteres");

        StringCustomUtils.validarTamanio(telefono.trim(), 10, 10,
                "El teléfono es requerido y debe tener exactamente 10 caracteres");


    }


} // FIN DE LA CLASE MAESTROS
