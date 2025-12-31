package com.jsp.ecommerce.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jsp.ecommerce.entity.User;
import com.jsp.ecommerce.enums.UserRole;
import com.jsp.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminAccountCreator implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;
	@Value("${admin.mobile}")
	private Long adminMobile;
	@Value("${admin.username}")
	private String adminUserName;

	@Override
	public void run(String... args) throws Exception {
		log.info("Admin Account Creation Started");
		if (userRepository.existsByEmail(adminEmail)) {
			log.info("Admin Account Already Exists");
		} else {
			User user = new User();
			user.setActive(true);
			user.setEmail(adminEmail);
			user.setMobile(adminMobile);
			user.setUsername(adminUserName);
			user.setPassword(passwordEncoder.encode(adminPassword));
			user.setRole(UserRole.ADMIN);
			userRepository.save(user);
			log.info("Admin Account Creaion Success - " + adminUserName);
		}
	}

}
