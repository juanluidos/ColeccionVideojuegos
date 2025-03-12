package com.coleccion.videojuegos.entity;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
    private String email;

	@Column(nullable = false)
	private String password;

	@Column(name="is_enabled")
	private boolean isEnabled;

	@Column(name="account_No_Expired")
	private boolean accountNoExpired;

	@Column(name="account_No_Locked")
	private boolean accountNoLocked;

	@Column(name="credential_No_Expired")
	private boolean credentialNoExpired;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
		name = "user_roles", 
		joinColumns = @JoinColumn(name="user_id"),  
		inverseJoinColumns = @JoinColumn(name="role_id")  
	)
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

	// Relación con Videojuego: Un usuario puede registrar varios videojuegos
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	@JsonManagedReference 
	private List<Videojuego> videojuegos = new ArrayList<>();

	public List<String> getRolesAsStrings() {
        return roles.stream()
                    .map(role -> role.getRole().name()) // Convierte RoleEnum a String
                    .toList();
    }
}
