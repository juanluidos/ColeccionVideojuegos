package com.coleccion.videojuegos.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.coleccion.videojuegos.entity.Usuario;

public interface UserRepository extends CrudRepository<Usuario, Integer>{

	Optional<Usuario>  findUserByUsername(String username);
}
