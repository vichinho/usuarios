package com.usuarios.usuarios.Service;

import com.usuarios.usuarios.Model.Rol;
import com.usuarios.usuarios.Model.Usuario;
import com.usuarios.usuarios.Repository.RolRepository;
import com.usuarios.usuarios.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    @Override
@Transactional
public Usuario asignarRol(Long usuarioId, Long rolId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    Rol rol = rolRepository.findById(rolId)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    
    usuario.setRol(rol);
    return usuarioRepository.save(usuario);
}
@Override
@Transactional
public void quitarRol(Long usuarioId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    usuario.setRol(null); // Elimina la relación
    usuarioRepository.save(usuario);
}
@Override
public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
    return usuarioRepository.findById(id)
            .map(usuarioExistente -> {
                // Actualizar solo campos no nulos
                if (usuarioActualizado.getNombre() != null) {
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                }
                if (usuarioActualizado.getApellido() != null) {
                    usuarioExistente.setApellido(usuarioActualizado.getApellido());
                }
                if (usuarioActualizado.getTelefono() != null) {
                    usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
                }
                if (usuarioActualizado.getRut() != null) {
                    usuarioExistente.setRut(usuarioActualizado.getRut());
                }
                if (usuarioActualizado.getEmail() != null) {
                    usuarioExistente.setEmail(usuarioActualizado.getEmail());
                }
                if (usuarioActualizado.getDireccion() != null) {
                    usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
                }
                return usuarioRepository.save(usuarioExistente);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
}
@Override
@Transactional
public void desactivarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    usuario.setActivo(false);
    // No necesitamos save() porque @Transactional actualiza automáticamente
}

@Override
@Transactional
public void activarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    usuario.setActivo(true);
}
}