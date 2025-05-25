package com.usuarios.usuarios.Service;

import com.usuarios.usuarios.Model.Rol;
import com.usuarios.usuarios.Repository.RolRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service // ¡Esta anotación es crucial!
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    // Inyección por constructor (recomendado)
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional
    public Rol actualizarRol(Long rolId, Rol rolActualizado) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        if(rolActualizado.getNombre() != null) {
            rol.setNombre(rolActualizado.getNombre().trim());
        }
        if(rolActualizado.getNombre() != null) {
            rol.setDescripcion(rolActualizado.getDescripcion().trim());
        }
        
        return rolRepository.save(rol);
    }
}