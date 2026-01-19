package com.jsp.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByMerchant(Merchant merchant);

	Optional<Product> findByIdAndMerchant(Long id, Merchant merchant);
	
	Page<Product> findByApprovedTrue(PageRequest of);

	Page<Product> findByApprovedTrueAndNameContaining(String name, PageRequest of);

	Page<Product> findByApprovedTrueAndCategory(String category, PageRequest of);

	Slice<Product> findByPriceBetweenAndApprovedTrue(double lowerRange, double higherRange, PageRequest of);
}
