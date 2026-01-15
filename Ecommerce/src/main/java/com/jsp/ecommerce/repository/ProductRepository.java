package com.jsp.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByMerchant(Merchant merchant);

	Optional<Product> findByIdAndMerchant(Long id, Merchant merchant);
}
