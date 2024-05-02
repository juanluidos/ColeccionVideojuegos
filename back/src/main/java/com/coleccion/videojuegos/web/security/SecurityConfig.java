package com.coleccion.videojuegos.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.coleccion.videojuegos.utils.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

	//Añadimos el filtro de JWT validacion a la propia cadena de filtros
	@Autowired
	private JwtUtils jwtUtils;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // Configurar los endpoints publicos
                    http.requestMatchers(HttpMethod.GET, "/public/**").permitAll();

					//Configurar los endpoints de gestion de usuarios
					http.requestMatchers(HttpMethod.GET, "auth/**").permitAll();
					http.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
					http.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll();

                    // Cofnigurar los endpoints privados
                    http.requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("READ");
					http.requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("DELETE");

                    // http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyAuthority("REFACTOR");

                    // Configurar el resto de endpoint - NO ESPECIFICADOS
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class) //ejecutar antes del Basic authentication filter
				.build();
    }


    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return httpSecurity
    //         .csrf(csrf -> csrf.disable())
    //         .httpBasic(Customizer.withDefaults())
    //         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .authorizeHttpRequests(http ->  {
    //             http.requestMatchers(HttpMethod.GET, "/public/prueba").permitAll();
    //             http.requestMatchers(HttpMethod.GET, "/api/v1/videojuegos").hasAnyAuthority("READ"); // Updated to reflect default role prefix
    //             http.anyRequest().authenticated();
    //         })
    //         .build();
    // }
	

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	// //para pruebas bien ,produccion no o
	// @Bean
	// public PasswordEncoder passwordEncoder(){
	// 	return NoOpPasswordEncoder.getInstance();
	// }
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	// public static void main(String[] args) {
	// 	System.out.println(new BCryptPasswordEncoder().encode("1234"));
	// }
}
