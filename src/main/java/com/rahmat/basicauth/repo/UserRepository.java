package com.rahmat.basicauth.repo;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rahmat.basicauth.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Modifying
	@Query("update User u set u.tokenExpiry = :date where u.username = :username")
	void updateExpiredDate(@Param("date") Date date, @Param("username") String username);
}
