package com.rahmat.basicauth.model;

import javax.persistence.*;

import com.rahmat.basicauth.model.enumeration.ERole;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
}
