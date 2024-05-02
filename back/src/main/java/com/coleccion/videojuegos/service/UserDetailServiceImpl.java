package com.coleccion.videojuegos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.CustomUserDetails;
import com.coleccion.videojuegos.entity.Role;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.repository.RoleRepository;
import com.coleccion.videojuegos.repository.UserRepository;
import com.coleccion.videojuegos.utils.JwtUtils;
import com.coleccion.videojuegos.web.controllers.dto.AuthCreateRoleRequest;
import com.coleccion.videojuegos.web.controllers.dto.AuthCreateUserRequest;
import com.coleccion.videojuegos.web.controllers.dto.AuthLoginRequest;
import com.coleccion.videojuegos.web.controllers.dto.AuthResponse;


@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userRepository.findUserByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

		return new CustomUserDetails(usuario);
	}

	public AuthResponse loginUser(AuthLoginRequest authLoginRequest){

		//Generamos el token JWT de acceso
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();
		Authentication authentication = this.authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.createToken(authentication);
		AuthResponse authResponse = new AuthResponse(username, "Usuario loggeado satisfactoriamente", accessToken, true);
		return authResponse;
	}

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Usuario o contraseña inválidas"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

public AuthResponse createUser(AuthCreateUserRequest createRoleRequest) {
    String username = createRoleRequest.username();
    String password = createRoleRequest.password();
    AuthCreateRoleRequest roleRequest = createRoleRequest.roleRequest();

    // Comprobación para asegurar que roleRequest y roleListName no son null
    if (roleRequest == null || roleRequest.roleListName() == null) {
        throw new IllegalArgumentException("La solicitud de roles está incompleta o vacía.");
    }

    List<String> rolesRequest = roleRequest.roleListName();
    Set<Role> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest).stream().collect(Collectors.toSet());

    if (roleEntityList.isEmpty()) {
        throw new IllegalArgumentException("Los roles especificados no existen");
    }

    Usuario userEntity = Usuario.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .roles(roleEntityList)
        .isEnabled(true)
        .accountNoLocked(true)
        .accountNoExpired(true)
        .credentialNoExpired(true)
        .build();

    Usuario userSaved = userRepository.save(userEntity);
    ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

    userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name())));
    userSaved.getRoles().stream().flatMap(role -> role.getPermissionList().stream())
        .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

    Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);
    String accessToken = jwtUtils.createToken(authentication);

    return new AuthResponse(username, "Usuario creado satisfactoriamente ", accessToken, true);
}

	
}