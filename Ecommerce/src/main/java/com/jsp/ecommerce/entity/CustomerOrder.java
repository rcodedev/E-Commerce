package com.jsp.ecommerce.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double amount;
	private String adrress;
	private boolean paymentStatus;

	private String orderId;
	private String paymentId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Item> items;

	@ManyToOne
	Customer customer;
}
