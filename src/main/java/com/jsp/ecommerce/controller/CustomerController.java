package com.jsp.ecommerce.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ecommerce.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping("/products")
	public Map<String, Object> getProducts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "false") boolean desc, @RequestParam(required = false) String name,
			@RequestParam(required = false) String category, @RequestParam(required = false) String lower,
			@RequestParam(required = false) String higher) {
		return customerService.getProducts(page,size,sort,desc,name,category,lower,higher);
	}
}
