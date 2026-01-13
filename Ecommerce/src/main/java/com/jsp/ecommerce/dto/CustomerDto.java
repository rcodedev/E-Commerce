package com.jsp.ecommerce.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotEmpty(message = "Name is Required")
	private String name;
	@NotEmpty(message = "Email is Required")
	private String email;
	@NotEmpty(message = "Password is Required")
	private String password;
	@NotNull(message = "Mobile is Required")
	private Long mobile;
	@NotEmpty(message = "Address is Required")
	private String address;

	@JsonProperty(access = Access.READ_ONLY)
	private String status;

}
