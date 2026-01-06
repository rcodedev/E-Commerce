package com.jsp.ecommerce.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce.dao.UserDao;
import com.jsp.ecommerce.dto.LoginDto;
import com.jsp.ecommerce.entity.User;
import com.jsp.ecommerce.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	public Map<String, Object> login(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		String token = jwtService.generateToken(userDetails);
		return Map.of("message", "Login Success", "token", token);
	}
	
	@Override
	public Map<String, Object> viewUser(String email) {
		User user = userDao.findByEmail(email);
		return Map.of("message", "Data Found", "user", user);
	}

	@Override
	public Map<String, Object> updatePassword(String email, String oldPassword, String newPassword) {
		User user = userDao.findByEmail(email);
		if (passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userDao.save(user);
			return Map.of("message", "Password Updated Success", "user", user);
		}
		throw new IllegalArgumentException("Old Password Not Matching");
	}


}
