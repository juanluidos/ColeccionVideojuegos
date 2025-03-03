package com.coleccion.videojuegos;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coleccion.videojuegos.entity.Permiso;
import com.coleccion.videojuegos.entity.Role;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.entity.Enums.RoleEnum;
import com.coleccion.videojuegos.repository.UserRepository;

@SpringBootApplication
@EntityScan(basePackages = {"com.coleccion.videojuegos.entity"})
public class VideojuegosApplication{

    public static void main(String[] args) {
        SpringApplication.run(VideojuegosApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (userRepository.findUserByUsername("Juanlu").isEmpty()) {
                Usuario userJuanlu = Usuario.builder()
                        .username("Juanlu")
                        .password(passwordEncoder.encode("1234"))
                        .email("juanlu@email.com")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(new Role(null, RoleEnum.ADMIN, Set.of())))
                        .build();

                Usuario userNormal = Usuario.builder()
                        .username("Normal")
                        .password(passwordEncoder.encode("1234"))
                        .email("normal@email.com")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(new Role(null, RoleEnum.USER, Set.of())))
                        .build();

                //userRepository.saveAll(List.of(userJuanlu, userNormal));
            }
        };
    }
}
