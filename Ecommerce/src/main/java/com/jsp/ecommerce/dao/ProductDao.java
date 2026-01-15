package com.jsp.ecommerce.dao;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDao {

	private final ProductRepository productRepository;

	public void save(Product product) {
		productRepository.save(product);
	}

	public List<Product> getMerchantProducts(Merchant merchant) {
		List<Product> products = productRepository.findByMerchant(merchant);
		if (products.isEmpty())
			throw new NoSuchElementException("No Products for Merchant : " + merchant.getName());
		else
			return products;
	}

	public Product getProductByIdAndMerchant(Long id, Merchant merchant) {
		return productRepository.findByIdAndMerchant(id, merchant).orElseThrow(()->new NoSuchElementException("No Product Found with Id: "+id));
	}

	public void delete(Product product) {
		productRepository.delete(product);
	}
}
