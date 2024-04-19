package com.coleccion.videojuegos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Videojuego;

@Repository
public interface ProgresoRepository extends CrudRepository<Progreso, Integer> {
	Optional<Progreso> findById(int id);

	void deleteById(int id);

    List<Progreso> findAll();
}
