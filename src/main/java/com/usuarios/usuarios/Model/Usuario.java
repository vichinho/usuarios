package com.usuarios.usuarios.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String rut;
    private String email;
    private String direccion;
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = true)
    private Rol rol;
}