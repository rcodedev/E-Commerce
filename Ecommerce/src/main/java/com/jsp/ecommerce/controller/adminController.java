package com.jsp.ecommerce.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class adminController {

	@GetMapping("/check")
	@PreAuthorize("hasRole('ADMIN')")
	public String check() {
		return "Working !!!";
	}
}
