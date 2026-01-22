package com.jsp.ecommerce.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
		return customerService.getProducts(page, size, sort, desc, name, category, lower, higher);
	}

	@PostMapping("/cart/product/{id}")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> addToCart(@PathVariable Long id, Principal principal, @RequestParam String size) {
		return customerService.addToCart(id, principal.getName(), size);
	}

	@GetMapping("/cart")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> viewCart(Principal principal) {
		return customerService.viewCart(principal.getName());
	}

	@DeleteMapping("/cart/product/{id}")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> removeFromCart(@PathVariable Long id, Principal principal) {
		return customerService.removeFromCart(id, principal.getName());
	}
	

	@PostMapping("/cart/buy")
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> buyFromCart(Principal principal, @RequestParam String address) {
		return customerService.buyFromCart(principal.getName(), address);
	}
	
	@PostMapping("/confirm-payment/{id}")
	public Map<String, Object> confirmPayment(@PathVariable Long id,@RequestParam String razorpay_payment_id){
		return customerService.confirmPayment(id,razorpay_payment_id);
	}
	
	@GetMapping("/orders")
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> viewOrders(Principal principal) {
		return customerService.getAllOrders(principal.getName());
	}
}
