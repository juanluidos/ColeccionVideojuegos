package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.CustomUserDetails;
import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.repository.UserRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

import jakarta.transaction.Transactional;

@Service
public class VideojuegosService {
    @Autowired
    private VideojuegoRepository videojuegoRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private SoporteService soporteService;

    @Autowired
    private ProgresoService progresoService;

    private Videojuego get(Optional<Videojuego> videojuego) {
        return videojuego.orElse(null);
    }

    /**   Obtener todos los videojuegos **/
    public List<Videojuego> listAllVideojuegos() {
        return videojuegoRepository.findAll();
    }

    /**   Obtener videojuego por ID **/
    public Videojuego getVideojuego(Integer id) {
        return get(videojuegoRepository.findById(id));
    }

	public List<Videojuego> getVideojuegosByUsuario(String username) {
		System.out.println("Buscando videojuegos de usuario: " + username); // Debug
		return videojuegoRepository.findByUsuario_Username(username);
	}	
	


	public Videojuego newVideojuego(VideojuegoCompletoRequest vRequest, String username) {
		// 🔹 Buscar el usuario autenticado en la base de datos
		Usuario usuario = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	
		// 🔹 Crear el videojuego con el usuario asociado
		Videojuego videojuego = Videojuego.builder()
				.nombre(vRequest.getNombre())
				.precio(vRequest.getPrecio())
				.fechaLanzamiento(vRequest.getFechaLanzamiento())
				.fechaCompra(vRequest.getFechaCompra())
				.plataforma(vRequest.getPlataforma())
				.genero(vRequest.getGenero())
				.usuario(usuario)
				.build();
	
		return videojuegoRepository.save(videojuego);
	}
	
	public Videojuego updateVideojuego(Integer id, VideojuegoRequest vRequest) {
		Optional<Videojuego> videojuegoOptional = videojuegoRepository.findById(id);
		if (videojuegoOptional.isEmpty()) {
			throw new RuntimeException("No existe dicho videojuego en la base de datos.");
		}
	
		Videojuego videojuego = videojuegoOptional.get();
	
		// Actualizar solo si los valores no son nulos y han cambiado
		if (vRequest.getNombre() != null) {
			videojuego.setNombre(vRequest.getNombre());
		}
		if (vRequest.getPrecio() != null) {
			videojuego.setPrecio(vRequest.getPrecio());
		}
		if (vRequest.getFechaLanzamiento() != null) {
			videojuego.setFechaLanzamiento(vRequest.getFechaLanzamiento());
		}
		if (vRequest.getFechaCompra() != null) {
			videojuego.setFechaCompra(vRequest.getFechaCompra());
		}
		if (vRequest.getPlataforma() != null) {
			videojuego.setPlataforma(vRequest.getPlataforma());
		}
		if (vRequest.getGenero() != null) {
			videojuego.setGenero(vRequest.getGenero());
		}
	
		return videojuegoRepository.save(videojuego);
	}
	
	public void deleteVideojuego(Integer id) {
		if (!videojuegoRepository.existsById(id)) {
			throw new RuntimeException("El videojuego con ID " + id + " no existe.");
		}
		videojuegoRepository.deleteById(id);
	}	

    /**   Obtener progresos de un videojuego **/
    public List<Progreso> getProgresoListByVideojuego(Integer idVideojuego) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
        return videojuegoOpt.map(Videojuego::getProgreso).orElse(List.of());
    }

    /**   Obtener soportes de un videojuego **/
    public List<Soporte> getSoporteListByVideojuego(Integer idVideojuego) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
        return videojuegoOpt.map(Videojuego::getSoporte).orElse(List.of());
    }
}