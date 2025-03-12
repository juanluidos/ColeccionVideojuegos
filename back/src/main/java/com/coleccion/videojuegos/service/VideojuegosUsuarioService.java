package com.coleccion.videojuegos.service;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.repository.UserRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.utils.AuthorizationUtils;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VideojuegosUsuarioService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @Autowired
    private UserRepository userRepository;
    
    /** ✅ Obtener los videojuegos de un usuario **/
    public List<Videojuego> getVideojuegosByUsuario(String username) {
        return videojuegoRepository.findByUsuario_Username(username);
    }

    /** ✅ Obtener un videojuego por ID (solo si es dueño) **/
    public Videojuego getVideojuego(Integer id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));

        return videojuego;
    }

    /** ✅ Crear un nuevo videojuego **/
    public Videojuego newVideojuego(VideojuegoCompletoRequest vRequest, String username) {
        Usuario usuario = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Videojuego videojuego = Videojuego.builder()
                .nombre(vRequest.getNombre())
                .precio(vRequest.getPrecio())
                .fechaLanzamiento(vRequest.getFechaLanzamiento())
                .fechaCompra(vRequest.getFechaCompra())
                .plataforma(vRequest.getPlataforma())
                .genero(vRequest.getGenero())
                .usuario(usuario)
                .build();

        if (vRequest.getProgreso() != null) {
            vRequest.getProgreso().forEach(p -> {
                Progreso progreso = new Progreso(null, p.getAnyoJugado(), p.getAvance(), p.getHorasJugadas(), p.getCompletadoCien(), p.getNota(), videojuego);
                videojuego.addProgreso(progreso);
            });
        }

        if (vRequest.getSoporte() != null) {
            vRequest.getSoporte().forEach(s -> {
                Soporte soporte = new Soporte(null, s.getTipo(), s.getEstado(), s.getEdicion(), s.getDistribucion(), s.getPrecintado(), s.getRegion(), s.getAnyoSalidaDist(), s.getTienda(), videojuego);
                videojuego.addSoporte(soporte);
            });
        }

        return videojuegoRepository.save(videojuego);
    }

    /** ✅ Editar un videojuego (solo si es dueño) **/
    public Videojuego updateVideojuego(Integer id, VideojuegoCompletoRequest vRequest, String username) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));

        if (!videojuego.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permisos para modificar este videojuego");
        }

        videojuego.setNombre(vRequest.getNombre());
        videojuego.setPrecio(vRequest.getPrecio());
        videojuego.setFechaLanzamiento(vRequest.getFechaLanzamiento());
        videojuego.setFechaCompra(vRequest.getFechaCompra());
        videojuego.setPlataforma(vRequest.getPlataforma());
        videojuego.setGenero(vRequest.getGenero());

        videojuego.getProgreso().clear();
        if (vRequest.getProgreso() != null) {
            vRequest.getProgreso().forEach(p -> {
                Progreso progreso = new Progreso(null, p.getAnyoJugado(), p.getAvance(), p.getHorasJugadas(), p.getCompletadoCien(), p.getNota(), videojuego);
                videojuego.addProgreso(progreso);
            });
        }

        videojuego.getSoporte().clear();
        if (vRequest.getSoporte() != null) {
            vRequest.getSoporte().forEach(s -> {
                Soporte soporte = new Soporte(null, s.getTipo(), s.getEstado(), s.getEdicion(), s.getDistribucion(), s.getPrecintado(), s.getRegion(), s.getAnyoSalidaDist(), s.getTienda(), videojuego);
                videojuego.addSoporte(soporte);
            });
        }

        return videojuegoRepository.save(videojuego);
    }

    /** ✅ Eliminar un videojuego **/
    @Transactional
    public void deleteVideojuego(Integer id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El videojuego con ID " + id + " no existe."));

        videojuego.getProgreso().clear();
        videojuego.getSoporte().clear();
        videojuegoRepository.save(videojuego);

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
