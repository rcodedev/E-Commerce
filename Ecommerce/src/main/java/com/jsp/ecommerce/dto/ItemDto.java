package com.jsp.ecommerce.dto;

import lombok.Data;

@Data
public class ItemDto {

	private String name;
	private String brand;
	private Integer quantity;
	private String category;
	private String size;
	private Double price;
	private Long productId;
}
