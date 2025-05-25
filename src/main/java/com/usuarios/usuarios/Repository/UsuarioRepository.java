package com.usuarios.usuarios.Repository;

import com.usuarios.usuarios.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByRut(String rut);
    boolean existsByEmail(String email);
}