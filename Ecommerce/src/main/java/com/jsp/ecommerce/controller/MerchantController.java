package com.jsp.ecommerce.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ecommerce.dto.ProductDto;
import com.jsp.ecommerce.service.MerchantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

	private final MerchantService merchantService;

	@PostMapping("/products")
	@PreAuthorize("hasRole('MERCHANT')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> addProduct(@RequestBody @Valid ProductDto productDto, Principal principal) {
		return merchantService.saveProduct(productDto, principal.getName());
	}

	@GetMapping("/products")
	@PreAuthorize("hasRole('MERCHANT')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getProducts(Principal principal) {
		return merchantService.getProducts(principal.getName());
	}

	@DeleteMapping("/products/{id}")
	@PreAuthorize("hasRole('MERCHANT')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> deleteProduct(@PathVariable Long id, Principal principal) {
		return merchantService.deleteProduct(id, principal.getName());
	}

	@PutMapping("/products/{id}")
	@PreAuthorize("hasRole('MERCHANT')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> updateProduct(@PathVariable Long id,@RequestBody @Valid ProductDto productDto, Principal principal) {
		return merchantService.updateProduct(id,productDto, principal.getName());
	}
	
	
	@PostMapping("/add-extras")
	@PreAuthorize("hasRole('MERCHANT')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> addProducts(Principal principal) {
		return merchantService.addProducts(principal.getName());
	}
}
