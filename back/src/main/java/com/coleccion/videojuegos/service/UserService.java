package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.Role;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.entity.Enums.RoleEnum;
import com.coleccion.videojuegos.repository.RoleRepository;
import com.coleccion.videojuegos.repository.UserRepository;
import com.coleccion.videojuegos.utils.JwtUtils;
import com.coleccion.videojuegos.web.controllers.dto.AuthCreateUserRequest;
import com.coleccion.videojuegos.web.controllers.dto.AuthResponse;

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
    public AuthResponse registerUser(AuthCreateUserRequest userRequest) {
        if (userRepository. findUserByUsername(userRequest.username()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
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
        newUser.setRoles(Set.of(userRole)); // 🔥 IMPORTANTE: Se asigna correctamente como un Set

        userRepository.save(newUser);

        // ✅ Generar token con las autoridades del usuario
        List<SimpleGrantedAuthority> authorities = newUser.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
        .toList();

        String accessToken = jwtUtils.createToken(
            new UsernamePasswordAuthenticationToken(newUser.getUsername(), null, authorities));

        return new AuthResponse(newUser.getUsername(), "Usuario registrado satisfactoriamente", accessToken, true);
    }

    /**   Crear usuario (SOLO ADMIN PUEDE CREAR USUARIOS) **/
    public Usuario createUser(AuthCreateUserRequest userRequest, Usuario adminUser) {
        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            throw new RuntimeException("Solo los administradores pueden crear usuarios");
        }

        if (userRepository. findUserByUsername(userRequest.username()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario newUser = new Usuario();
        newUser.setUsername(userRequest.username());
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        newUser.setEmail(userRequest.email());
        newUser.setEnabled(true);
        newUser.setAccountNoExpired(true);
        newUser.setAccountNoLocked(true);
        newUser.setCredentialNoExpired(true);

        // ✅ Obtener los roles del request, o asignar USER por defecto
        List<RoleEnum> roleEnums = (userRequest.roleRequest() != null && !userRequest.roleRequest().roleListName().isEmpty())
                ? userRequest.roleRequest().roleListName().stream().map(RoleEnum::valueOf).toList()
                : List.of(RoleEnum.USER);

        List<Role> userRoles = roleRepository.findByRoleIn(roleEnums);
        newUser.setRoles(Set.copyOf(userRoles));

        return userRepository.save(newUser);
    }

        /** ✅ Obtener el usuario autenticado desde el contexto de seguridad **/
    public Usuario getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    /**   Obtener todos los usuarios **/
    public List<Usuario> getAllUsers() {
        return (List<Usuario>) userRepository.findAll();
    }

    /**   Obtener usuario por ID **/
    public Usuario getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**   Eliminar usuario (SOLO ADMIN) **/
    public void deleteUser(Integer id, Usuario adminUser) {
        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            throw new RuntimeException("Solo los administradores pueden eliminar usuarios");
        }

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    /**   Actualizar roles de un usuario (SOLO ADMIN) **/
    public Usuario updateUserRoles(Integer id, List<String> roles, Usuario adminUser) {
        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            throw new RuntimeException("Solo los administradores pueden modificar roles");
        }

        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<RoleEnum> roleEnums = roles.stream().map(RoleEnum::valueOf).toList();
        List<Role> newRoles = roleRepository.findByRoleIn(roleEnums);

        usuario.setRoles(Set.copyOf(newRoles));
        return userRepository.save(usuario);
    }
}
