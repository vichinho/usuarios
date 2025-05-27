package com.usuarios.usuarios.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permisos")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nombre;
    private String descripcion;
}