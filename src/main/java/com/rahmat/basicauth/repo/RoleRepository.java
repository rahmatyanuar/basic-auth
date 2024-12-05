package com.rahmat.basicauth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahmat.basicauth.model.Role;
import com.rahmat.basicauth.model.enumeration.ERole;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
