package com.jsp.ecommerce.dto;

import java.util.List;

import com.jsp.ecommerce.entity.Item;

import lombok.Data;

@Data
public class OrderDto {

	private String address;
	private Double amount;
	private List<Item> items;
	private String paymentId;
	private String orderId;
}
