package com.coleccion.videojuegos;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

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
	CommandLineRunner init(UserRepository userRepository){
		return args -> {
			/* CREATE PERMISOS */
			Permiso createPermiso = Permiso.builder().name("CREATE").build();
			Permiso readPermiso = Permiso.builder().name("READ").build();
			Permiso updatePermiso = Permiso.builder().name("UPDATE").build();
			Permiso deletePermiso = Permiso.builder().name("DELETE").build();

			/* CREATE ROLES */

			Role roleAdmin = Role.builder()
            .role(RoleEnum.ADMIN)
            .build(); // Primero construimos el Role
			// Luego añadimos permisos al rol
			roleAdmin.getPermissionList().addAll(Set.of(createPermiso, readPermiso, updatePermiso, deletePermiso));

			Role rolseUser = Role.builder()
            .role(RoleEnum.USER)
            .build(); // Primero construimos el Role
			// Luego añadimos permisos al rol
			rolseUser.getPermissionList().addAll(Set.of(readPermiso));

			/* CREATE USUARIOS */

			Usuario userJuanlu = Usuario.builder()
				.username("Juanlu")
				.password("$2a$10$LMA7SDG55rtrPpSukM1d2eRSGY7YEtb5N6Lyg.etSVMyfJnc1.E9y")
				.isEnabled(true)
				.accountNoExpired(true)
				.accountNoLocked(true)
				.credentialNoExpired(true)
				.roles(Set.of(roleAdmin))
				.build();

				Usuario userNormal = Usuario.builder()
				.username("Normal")
				.password("$2a$10$LMA7SDG55rtrPpSukM1d2eRSGY7YEtb5N6Lyg.etSVMyfJnc1.E9y")
				.isEnabled(true)
				.accountNoExpired(true)
				.accountNoLocked(true)
				.credentialNoExpired(true)
				.roles(Set.of(rolseUser))
				.build();

			//userRepository.saveAll(List.of(userJuanlu, userNormal));
		
		
		};
	}

}
