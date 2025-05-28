package com.usuarios.usuarios.Controller;

import com.usuarios.usuarios.Model.Usuario;
import com.usuarios.usuarios.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Validated @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{usuarioId}/asignar-rol/{rolId}")
public ResponseEntity<Usuario> asignarRol(
        @PathVariable Long usuarioId,
        @PathVariable Long rolId) {
    
    Usuario usuarioActualizado = usuarioService.asignarRol(usuarioId, rolId);
    return ResponseEntity.ok(usuarioActualizado);
}
@DeleteMapping("/{usuarioId}/quitar-rol")
public ResponseEntity<Void> quitarRol(@PathVariable Long usuarioId) {
    usuarioService.quitarRol(usuarioId);
    return ResponseEntity.noContent().build();
}
@PutMapping("/actualizar/{usuarioId}")  
public ResponseEntity<Usuario> actualizarUsuario(
        @PathVariable Long usuarioId,
        @Validated @RequestBody Usuario usuarioActualizado) {
    
    Usuario usuario = usuarioService.actualizarUsuario(usuarioId, usuarioActualizado);
    return ResponseEntity.ok(usuario);
}
@PatchMapping("/{id}/desactivar")
public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
    usuarioService.desactivarUsuario(id);
    return ResponseEntity.noContent().build();
}

@PatchMapping("/{id}/activar")
public ResponseEntity<Void> activarUsuario(@PathVariable Long id) {
    usuarioService.activarUsuario(id);
    return ResponseEntity.noContent().build();
}
}
