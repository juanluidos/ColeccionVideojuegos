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
	


	public Videojuego newVideojuego(VideojuegoCompletoRequest vRequest, String username){
		Usuario usuario = userRepository.findUserByUsername(username)
			.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	
		// Creamos el videojuego SIN el campo "tipo"
		Videojuego videojuego = Videojuego.builder()
			.nombre(vRequest.getNombre())
			.precio(vRequest.getPrecio())
			.fechaLanzamiento(vRequest.getFechaLanzamiento())
			.fechaCompra(vRequest.getFechaCompra())
			.plataforma(vRequest.getPlataforma())
			.genero(vRequest.getGenero())
			.usuario(usuario)
			.build();
	
		// ✅ Si hay progresos, los agregamos
		if (vRequest.getProgreso() != null) {
			vRequest.getProgreso().forEach(p -> {
				Progreso progreso = Progreso.builder()
					.anyoJugado(p.getAnyoJugado())
					.avance(p.getAvance())
					.horasJugadas(p.getHorasJugadas())
					.completadoCien(p.getCompletadoCien())
					.nota(p.getNota())
					.videojuego(videojuego)
					.build();
				videojuego.addProgreso(progreso);
			});
		}
	
		// ✅ Si hay soportes, los agregamos
		if (vRequest.getSoporte() != null) {
			vRequest.getSoporte().forEach(s -> {
				Soporte soporte = Soporte.builder()
					.tipo(s.getTipo()) // ⚡ Aquí sí se usa "tipo"
					.estado(s.getEstado())
					.edicion(s.getEdicion())
					.distribucion(s.getDistribucion())
					.precintado(s.getPrecintado())
					.region(s.getRegion())
					.anyoSalidaDist(s.getAnyoSalidaDist())
					.tienda(s.getTienda())
					.videojuego(videojuego)
					.build();
				videojuego.addSoporte(soporte);
			});
		}
	
		return videojuegoRepository.save(videojuego);
	}
	
    public Videojuego updateVideojuego(Integer id, VideojuegoCompletoRequest vRequest, String username) {
        // 1️⃣ Buscar el videojuego existente
        Videojuego videojuego = videojuegoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));
    
        // 2️⃣ Verificar que el usuario sea el dueño del videojuego
        if (!videojuego.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permisos para modificar este videojuego");
        }
    
        // 3️⃣ Actualizar los datos básicos del videojuego
        videojuego.setNombre(vRequest.getNombre());
        videojuego.setPrecio(vRequest.getPrecio());
        videojuego.setFechaLanzamiento(vRequest.getFechaLanzamiento());
        videojuego.setFechaCompra(vRequest.getFechaCompra());
        videojuego.setPlataforma(vRequest.getPlataforma());
        videojuego.setGenero(vRequest.getGenero());
    
        // 4️⃣ Actualizar los progresos (si los hay)
        if (vRequest.getProgreso() != null) {
            videojuego.getProgreso().clear();
            vRequest.getProgreso().forEach(p -> {
                Progreso progreso = Progreso.builder()
                    .anyoJugado(p.getAnyoJugado())
                    .avance(p.getAvance())
                    .horasJugadas(p.getHorasJugadas())
                    .completadoCien(p.getCompletadoCien())
                    .nota(p.getNota())
                    .videojuego(videojuego)
                    .build();
                videojuego.addProgreso(progreso);
            });
        }
    
        // 5️⃣ Actualizar los soportes (si los hay)
        if (vRequest.getSoporte() != null) {
            videojuego.getSoporte().clear();
            vRequest.getSoporte().forEach(s -> {
                Soporte soporte = Soporte.builder()
                    .tipo(s.getTipo()) 
                    .estado(s.getEstado())
                    .edicion(s.getEdicion())
                    .distribucion(s.getDistribucion())
                    .precintado(s.getPrecintado())
                    .region(s.getRegion())
                    .anyoSalidaDist(s.getAnyoSalidaDist())
                    .tienda(s.getTienda())
                    .videojuego(videojuego)
                    .build();
                videojuego.addSoporte(soporte);
            });
        }
    
        // 6️⃣ Guardar cambios en la BD y retornar el videojuego actualizado
        return videojuegoRepository.save(videojuego);
    }
    
	
    public void deleteVideojuego(Integer id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El videojuego con ID " + id + " no existe."));
    
        // 🔹 Eliminamos la relación con Progreso y Soporte antes de eliminar el videojuego
        videojuego.getProgreso().clear();
        videojuego.getSoporte().clear();
        videojuegoRepository.save(videojuego);
    
        // 🔹 Ahora eliminamos el videojuego
        videojuegoRepository.delete(videojuego);
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