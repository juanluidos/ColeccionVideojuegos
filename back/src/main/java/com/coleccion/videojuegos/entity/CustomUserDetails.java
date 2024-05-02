package com.coleccion.videojuegos.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
            .collect(Collectors.toList());

        usuario.getRoles().stream()
            .flatMap(role -> role.getPermissionList().stream())
            .forEach(permiso -> authorities.add(new SimpleGrantedAuthority(permiso.getName())));

        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return usuario.isAccountNoExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.isAccountNoLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.isCredentialNoExpired();
    }

    @Override
    public boolean isEnabled() {
        return usuario.isEnabled();
    }
}
