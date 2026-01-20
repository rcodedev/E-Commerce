package com.jsp.ecommerce.exception;

public class OutOfStockException extends RuntimeException {

	public OutOfStockException(String message) {
		super(message);
	}
}
