package com.coleccion.videojuegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.coleccion.videojuegos.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    // Usando @Query para definir una consulta JPQL para encontrar roles por los nombres de los enums
    @Query("select r from Role r where r.role in ?1")
	List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

}
