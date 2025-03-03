package com.coleccion.videojuegos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.coleccion.videojuegos.entity.Role;
import com.coleccion.videojuegos.entity.Enums.RoleEnum;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    
    /** Buscar un rol específico por su Enum **/
    Optional<Role> findByRole(RoleEnum role);

    /** Buscar múltiples roles por nombre (Lista de RoleEnum) **/
    @Query("SELECT r FROM Role r WHERE r.role IN ?1")
    List<Role> findByRoleIn(List<RoleEnum> roles);
}
