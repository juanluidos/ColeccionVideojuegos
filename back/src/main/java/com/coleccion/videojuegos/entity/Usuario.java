package com.coleccion.videojuegos.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

	@Column(unique = true)
	private String username;

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
		joinColumns = @JoinColumn(name="user_id"),  // Columna en la tabla de unión que se refiere a la entidad Usuario
		inverseJoinColumns = @JoinColumn(name="role_id")  // Columna en la tabla de unión que se refiere a la entidad Role
	)
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

	
}
