package com.coleccion.videojuegos.entity;

import java.util.HashSet;
import java.util.Set;

import com.coleccion.videojuegos.entity.Enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinTable(
		name = "role_permissions", 
		joinColumns = @JoinColumn(name="role_id"),
		inverseJoinColumns = @JoinColumn(name="permission_id")
	)	
    @Builder.Default
    private Set<Permiso> permissionList = new HashSet<>();
	
}

