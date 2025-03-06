package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coleccion.videojuegos.entity.CustomUserDetails;
import com.coleccion.videojuegos.entity.Role;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.entity.Enums.RoleEnum;
import com.coleccion.videojuegos.repository.RoleRepository;
import com.coleccion.videojuegos.repository.UserRepository;
import com.coleccion.videojuegos.utils.JwtUtils;
import com.coleccion.videojuegos.web.dto.AuthCreateUserRequest;
import com.coleccion.videojuegos.web.dto.AuthResponse;
import com.coleccion.videojuegos.web.dto.UserDTO;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**   Registro de usuario (permite que cualquier usuario se registre con rol USER) **/
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthCreateUserRequest userRequest) {
        if (userRepository.findUserByUsername(userRequest.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
        }

        Usuario newUser = new Usuario();
        newUser.setUsername(userRequest.username());
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        newUser.setEmail(userRequest.email());
        newUser.setEnabled(true);
        newUser.setAccountNoExpired(true);
        newUser.setAccountNoLocked(true);
        newUser.setCredentialNoExpired(true);

        // ✅ Asignar el rol por defecto "USER"
        Role userRole = roleRepository.findByRole(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        newUser.setRoles(Set.of(userRole));

        userRepository.save(newUser);

        // ✅ Generar UserDetails basado en el usuario creado
        CustomUserDetails userDetails = new CustomUserDetails(newUser);

        // ✅ Generar token con las autoridades correctas
        String accessToken = jwtUtils.createToken(
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(newUser.getUsername(), "Usuario registrado satisfactoriamente", accessToken, true));
    }


    /**   Crear usuario (SOLO ADMIN PUEDE CREAR USUARIOS) **/
    public ResponseEntity<?> createUser(AuthCreateUserRequest userRequest, String adminUsername) {
        Usuario adminUser = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo los administradores pueden crear usuarios.");
        }

        if (userRepository.findUserByUsername(userRequest.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
        }

        Usuario newUser = new Usuario();
        newUser.setUsername(userRequest.username());
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        newUser.setEmail(userRequest.email());
        newUser.setEnabled(true);
        newUser.setAccountNoExpired(true);
        newUser.setAccountNoLocked(true);
        newUser.setCredentialNoExpired(true);

        // ✅ Asignar roles al usuario
        List<RoleEnum> roleEnums = (userRequest.roleRequest() != null && !userRequest.roleRequest().roleListName().isEmpty())
                ? userRequest.roleRequest().roleListName().stream().map(RoleEnum::valueOf).toList()
                : List.of(RoleEnum.USER);

        List<Role> userRoles = roleRepository.findByRoleIn(roleEnums);
        newUser.setRoles(Set.copyOf(userRoles));

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente.");
    }

        /** ✅ Obtener el usuario autenticado desde el contexto de seguridad **/
    public ResponseEntity<?> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No estás autenticado.");
        }
    
        String username = authentication.getName();
        Usuario usuario = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    
        // ✅ Devolvemos un DTO en vez de la entidad completa
        UserDTO userDTO = new UserDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail());
    
        return ResponseEntity.ok(userDTO);
    }
        

    /**   Obtener todos los usuarios **/
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<Usuario> usuarios = (List<Usuario>) userRepository.findAll(); // Convertimos a List<Usuario> porq devuelve un Iterable el cual no se puede hacer stream
        
        List<UserDTO> users = usuarios.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail()))
                .toList();

        return ResponseEntity.ok(users);
    }


    /** ✅ Obtener usuario por ID **/
    public ResponseEntity<?> getUserById(Integer id) {
        Optional<Usuario> usuarioOptional = userRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            UserDTO userDTO = new UserDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    /**   Eliminar usuario (SOLO ADMIN) **/
    public ResponseEntity<?> deleteUser(Integer id, String adminUsername) {
        Usuario adminUser = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo los administradores pueden eliminar usuarios.");
        }

        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente.");
    }


    public ResponseEntity<?> updateUserRoles(Integer id, List<String> roles, String adminUsername) {
        Usuario adminUser = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));
    
        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo los administradores pueden modificar roles.");
        }
    
        Usuario usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        List<RoleEnum> roleEnums;
        try {
            roleEnums = roles.stream()
                .map(RoleEnum::valueOf)
                .toList();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uno o más roles enviados no son válidos.");
        }
    
        List<Role> newRoles = roleRepository.findByRoleIn(roleEnums);
    
        // ✅ Si los roles no han cambiado, no actualizamos la BD
        if (usuario.getRoles().containsAll(newRoles) && newRoles.containsAll(usuario.getRoles())) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("El usuario ya tiene estos roles.");
        }
    
        usuario.setRoles(Set.copyOf(newRoles));
        userRepository.save(usuario);
    
        return ResponseEntity.ok("Roles actualizados correctamente.");
    }    
}
