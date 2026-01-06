package com.jsp.ecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordDto {

	@NotEmpty(message = "Password is Required")
	private String oldPassword;
	@NotEmpty(message = "Password is Required")
	private String newPassword;

}
