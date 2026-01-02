package com.jsp.ecommerce.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@GetMapping("/check")
	@PreAuthorize("hasRole('ADMIN')")
	public String check() {
		return "Checked and Working !!!";
	}

	@GetMapping("/test")
	@PreAuthorize("hasRole('USER')")
	public String test() {
		return "Tested and Working !!!";
	}
}
