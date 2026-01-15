package com.jsp.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String brand;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private Integer stock;
	@Column(nullable = false)
	private String size;

	@ManyToOne
	private Merchant merchant;
}
