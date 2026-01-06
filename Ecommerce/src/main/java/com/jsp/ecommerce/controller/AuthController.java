package com.jsp.ecommerce.controller;


import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ecommerce.dto.LoginDto;
import com.jsp.ecommerce.dto.PasswordDto;
import com.jsp.ecommerce.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/login")
	public Map<String, Object> login(@Valid @RequestBody LoginDto loginDto) {
		return authService.login(loginDto.getEmail(),loginDto.getPassword());
	}
	
	@GetMapping("/me")
	@PreAuthorize("hasAnyRole('ADMIN','USER','MERCHANT')")
	public Map<String, Object> viewLoggedInUser(Principal principal) {
		return authService.viewUser(principal.getName());
	}

	@PatchMapping("/password")
	@PreAuthorize("hasAnyRole('ADMIN','USER','MERCHANT')")
	public Map<String, Object> updatePassword(Principal principal,@Valid @RequestBody PasswordDto passwordDto){
		return authService.updatePassword(principal.getName(),passwordDto.getOldPassword(),passwordDto.getNewPassword());
	}
		
	

}
