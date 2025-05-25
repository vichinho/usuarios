package com.usuarios.usuarios.Repository;

import com.usuarios.usuarios.Model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Rol r WHERE r.nombre = :nombre AND r.descripcion = :descripcion AND r.id != :id")
    boolean existsByNombreAndIdNot(@Param("nombre") String nombre,@Param("descripcion") String descripcion, @Param("id") Long id);
}