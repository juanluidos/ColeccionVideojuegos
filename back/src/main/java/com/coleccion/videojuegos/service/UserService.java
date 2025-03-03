package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private UserRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**   Registro de usuario (permite que cualquier usuario se registre con rol USER) **/
    public AuthResponse registerUser(AuthCreateUserRequest userRequest) {
        if (usuarioRepository.findUserByUsername(userRequest.username()).isPresent()) {
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

        usuarioRepository.save(newUser);

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

        if (usuarioRepository.findUserByUsername(userRequest.username()).isPresent()) {
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
        newUser.setRoles(Set.copyOf(userRoles)); // 🔥 IMPORTANTE: Se asigna correctamente como un Set

        return usuarioRepository.save(newUser);
    }

    /**   Obtener todos los usuarios **/
    public List<Usuario> getAllUsers() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    /**   Obtener usuario por ID **/
    public Usuario getUserById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**   Eliminar usuario (SOLO ADMIN) **/
    public void deleteUser(Integer id, Usuario adminUser) {
        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            throw new RuntimeException("Solo los administradores pueden eliminar usuarios");
        }

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    /**   Actualizar roles de un usuario (SOLO ADMIN) **/
    public Usuario updateUserRoles(Integer id, List<String> roles, Usuario adminUser) {
        if (!adminUser.getRoles().stream().anyMatch(r -> r.getRole().equals(RoleEnum.ADMIN))) {
            throw new RuntimeException("Solo los administradores pueden modificar roles");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<RoleEnum> roleEnums = roles.stream().map(RoleEnum::valueOf).toList();
        List<Role> newRoles = roleRepository.findByRoleIn(roleEnums);

        usuario.setRoles(Set.copyOf(newRoles)); // 🔥 Se asignan correctamente
        return usuarioRepository.save(usuario);
    }
}
