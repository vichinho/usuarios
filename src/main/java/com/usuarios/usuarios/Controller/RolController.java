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

    
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        return rolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<Rol> crearRol(@Validated @RequestBody Rol rolRequest) {
        Rol rol = new Rol();
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        Rol rolGuardado = rolRepository.save(rol);
        return ResponseEntity.ok(rolGuardado);
    }

    
    @PostMapping("/{rolId}/asignar-permisos/{permisoId}")
    public ResponseEntity<Rol> asignarPermiso(
        @PathVariable Long rolId,
        @PathVariable Long permisoId) {
    
    Rol rol = rolRepository.findById(rolId)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
    
    
    Permiso permiso = permisoRepository.findById(permisoId)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado con ID: " + permisoId));
    
    
    if (rol.getPermisos().contains(permiso)) {
        throw new RuntimeException("El permiso ya está asignado a este rol");
    }
    
    
    rol.getPermisos().add(permiso);
    Rol rolActualizado = rolRepository.save(rol);
    
    return ResponseEntity.ok(rolActualizado);
}
@DeleteMapping("/{rolId}/quitar-permisos/{permisoId}")
public ResponseEntity<?> eliminarPermisoDeRol(
        @PathVariable Long rolId,
        @PathVariable Long permisoId) {
    Rol rol = rolRepository.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
    Permiso permisoAEliminar = rol.getPermisos().stream()
            .filter(p -> p.getId().equals(permisoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("El permiso con ID " + permisoId + " no está asignado a este rol"));
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
    return rolRepository.findById(id).map(rol -> {
        rolRepository.delete(rol);
        return ResponseEntity.ok().build();}).orElse(ResponseEntity.notFound().build());
    }
}