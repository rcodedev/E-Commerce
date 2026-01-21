package com.jsp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDto {

	private String key;
	private Double amount;
	private String currency;
	private String orderId;
	private String name;
	private String email;
	private Long contact;
	private String callBackUrl;
}
