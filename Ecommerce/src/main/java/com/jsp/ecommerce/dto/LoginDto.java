package com.jsp.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
	@NotEmpty(message = "Email is Required")
	@Email(message = "Email Format is Not Correct")
	private String email;
	@NotEmpty(message = "Password is Required")
	private String password;
}
