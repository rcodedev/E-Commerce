package com.jsp.ecommerce.service;

import java.util.Map;

public interface CustomerService {

	Map<String, Object> getProducts(int page, int size, String sort, boolean desc, String name, String category,
			String lowerRange, String higherRange);
	
	
	Map<String, Object> addToCart(Long id, String name, String size);

	Map<String, Object> viewCart(String email);

	Map<String, Object> removeFromCart(Long id, String email);

}
