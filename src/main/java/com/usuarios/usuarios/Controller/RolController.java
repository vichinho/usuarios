package com.usuarios.usuarios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.usuarios.usuarios.Model.Permiso;
import com.usuarios.usuarios.Model.Rol;
import com.usuarios.usuarios.Repository.PermisoRepository;
import com.usuarios.usuarios.Repository.RolRepository;
import com.usuarios.usuarios.Service.RolService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolService rolService;

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    // Obtener un rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        return rolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo rol
    @PostMapping
    public ResponseEntity<Rol> crearRol(@Validated @RequestBody Rol rolRequest) {
        Rol rol = new Rol();
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        Rol rolGuardado = rolRepository.save(rol);
        return ResponseEntity.ok(rolGuardado);
    }

    // Asignar múltiples permisos a un rol
    @PostMapping("/{rolId}/asignar-permisos/{permisoId}")
public ResponseEntity<Rol> asignarPermiso(
        @PathVariable Long rolId,
        @PathVariable Long permisoId) {
    
    // 1. Validar existencia del rol
    Rol rol = rolRepository.findById(rolId)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
    
    // 2. Validar existencia del permiso
    Permiso permiso = permisoRepository.findById(permisoId)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado con ID: " + permisoId));
    
    // 3. Verificar si ya está asignado
    if (rol.getPermisos().contains(permiso)) {
        throw new RuntimeException("El permiso ya está asignado a este rol");
    }
    
    // 4. Asignar permiso
    rol.getPermisos().add(permiso);
    Rol rolActualizado = rolRepository.save(rol);
    
    return ResponseEntity.ok(rolActualizado);
}
@DeleteMapping("/{rolId}/quitar-permisos/{permisoId}")
public ResponseEntity<?> eliminarPermisoDeRol(
        @PathVariable Long rolId,
        @PathVariable Long permisoId) {

    // 1. Buscar el rol y validar existencia
    Rol rol = rolRepository.findById(rolId)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));

    // 2. Verificar si el permiso está asignado al rol
    Permiso permisoAEliminar = rol.getPermisos().stream()
            .filter(p -> p.getId().equals(permisoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("El permiso con ID " + permisoId + " no está asignado a este rol"));

    // 3. Eliminar el permiso
    rol.getPermisos().remove(permisoAEliminar);
    rolRepository.save(rol);

    return ResponseEntity.ok().build();
}
@PutMapping("/{rolId}")
public ResponseEntity<Rol> actualizarRol(
        @PathVariable Long rolId,
        @Validated @RequestBody Rol rolActualizado) {
    
    Rol rol = rolService.actualizarRol(rolId, rolActualizado);
    return ResponseEntity.ok(rol);
}

// Eliminar un rol por ID
@DeleteMapping("/{id}")
public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
    return rolRepository.findById(id)
            .map(rol -> {
                rolRepository.delete(rol);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
}
}