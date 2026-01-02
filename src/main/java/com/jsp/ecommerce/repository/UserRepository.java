package com.jsp.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String adminEmail);

	Optional<User> findByEmail(String email);

}
