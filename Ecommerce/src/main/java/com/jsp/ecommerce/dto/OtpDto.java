package com.jsp.ecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OtpDto {

	@NotEmpty(message = "Email is Required")
	private String email;
	@NotNull(message = "OTP is Required")
	private Integer otp;
}
