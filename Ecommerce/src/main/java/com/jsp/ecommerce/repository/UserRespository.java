package com.jsp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.jsp.ecommerce.entity.User;

public interface UserRespository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String adminEmail);

	Optional<User> findByEmail(String email);
	
	

}
