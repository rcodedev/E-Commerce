package com.jsp.ecommerce.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ecommerce.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	
	private final AdminService adminService;
	
	
	@GetMapping("/merchants")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> viewMerchants() {
		return adminService.getAllMerchants();
	}

	@GetMapping("/customers")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> viewCustomers() {
		return adminService.getAllCustomers();
	}

}
