package com.jsp.ecommerce.dao;

import org.springframework.stereotype.Repository;

import com.jsp.ecommerce.entity.User;

import com.jsp.ecommerce.repository.UserRespository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDao {

	private final UserRespository userRepository;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow();
	}

	public void save(User user) {
		userRepository.save(user);
	}
}
