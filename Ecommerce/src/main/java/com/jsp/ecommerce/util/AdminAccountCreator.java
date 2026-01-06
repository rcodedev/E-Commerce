package com.jsp.ecommerce.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jsp.ecommerce.EcommerceApplication;
import com.jsp.ecommerce.Enum.UserRole;
import com.jsp.ecommerce.entity.User;
import com.jsp.ecommerce.repository.UserRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminAccountCreator implements CommandLineRunner{

    private final EcommerceApplication ecommerceApplication;
	
	private  final UserRespository userRespository;
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
		if(userRespository.existsByEmail(adminEmail)) {
			log.info("Admin Account Already Exists");
		}
		else {
//			User user = new User();
//			user.setActive(true);
//			user.setEmail(adminEmail);
//			user.setMobile(adminMobile);
//			user.setUsername(adminUserName);
//			user.setPassword(passwordEncoder.encode(adminPassword));
//			user.setRole(UserRole.ADMI);
			
			User user = new User(null, adminUserName, adminEmail, passwordEncoder.encode(adminPassword), adminMobile, UserRole.ADMIN, true);
			

			userRespository.save(user);
			log.info("Admin Account Creation Successfully");
			
			
			
		}
	}

}
