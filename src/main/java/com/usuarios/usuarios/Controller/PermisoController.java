// src/main/java/com/microserviciousuario/controlador/PermisoController.java
package com.usuarios.usuarios.Controller;

import com.usuarios.usuarios.Model.Permiso;
import com.usuarios.usuarios.Repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    @Autowired
    private PermisoRepository permisoRepository;

    @PostMapping
    public Permiso crearPermiso(@RequestBody Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @GetMapping
    public List<Permiso> listarPermisos() {
        return permisoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Long id) {
        return permisoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}