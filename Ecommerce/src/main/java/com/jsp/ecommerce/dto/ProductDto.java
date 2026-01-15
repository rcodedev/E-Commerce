package com.jsp.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotEmpty(message = "It is Required")
	private String name;
	@NotEmpty(message = "It is Required")
	private String brand;
	@NotEmpty(message = "It is Required")
	private String category;
	@NotEmpty(message = "It is Required")
	private String description;
	@NotNull(message = "It is Required")
	private Double price;
	@NotNull(message = "It is Required")
	private Integer stock;
	@NotEmpty(message = "It is Required")
	private String size;
}
