package com.usuarios.usuarios.Service;

import com.usuarios.usuarios.Model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> obtenerTodosUsuarios();
    Usuario obtenerUsuarioPorId(Long id);
    void eliminarUsuario(Long id);
    Usuario asignarRol(Long usuarioId, Long rolId);
    void quitarRol(Long usuarioId);
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
}