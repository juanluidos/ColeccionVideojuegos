package com.coleccion.videojuegos.web.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.coleccion.videojuegos.utils.JwtUtils;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{

	public JwtTokenValidator(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	private JwtUtils jwtUtils;
	

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {

		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if(jwtToken != null){ //Estructura del token -> Bearer jdvhdvhdkvjk , nosotros queremos quitar el Bearer y quedarnos solo con el propio token (jdvhdvhdkvjk)
			jwtToken = jwtToken.substring(7);
			DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

			String username = jwtUtils.extractUsername(decodedJWT);
			String stringAuthorithies = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

			Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorithies); //READ,WRITE,DELETE... separados por comas

			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
		}
		filterChain.doFilter(request, response);
	}


	
}
