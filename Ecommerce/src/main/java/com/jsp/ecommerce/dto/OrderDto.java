package com.jsp.ecommerce.dto;

import java.util.List;


import lombok.Data;

@Data
public class OrderDto {

	private String address;
	private Double amount;
	private List<ItemDto> items;
	private String paymentId;
	private String orderId;
}
