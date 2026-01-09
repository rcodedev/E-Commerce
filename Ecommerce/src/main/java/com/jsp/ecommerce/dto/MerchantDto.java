package com.jsp.ecommerce.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MerchantDto {

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
	@NotEmpty(message = "gstNo is Required")
	private String gstNo;
}
