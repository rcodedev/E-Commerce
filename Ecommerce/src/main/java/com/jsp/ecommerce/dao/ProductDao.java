package com.jsp.ecommerce.dao;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.jsp.ecommerce.entity.Item;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.repository.ItemRepository;
import com.jsp.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDao {

	private final ProductRepository productRepository;
	private final ItemRepository itemRepository;

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
	
	
	public List<Product> getProducts() {
		List<Product> products = productRepository.findAll();
		if (products.isEmpty())
			throw new NoSuchElementException("No Products found");
		else
			return products;
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(()->new NoSuchElementException("No Product with Id: "+id));
	}
	
	public List<Product> getAllApprovedProducts(int page, int size, String sort, boolean desc) {

		List<Product> products = productRepository
				.findByApprovedTrue(
						PageRequest.of(page - 1, size, desc ? Sort.by(sort).descending() : Sort.by(sort).ascending()))
				.getContent();
		if (products.isEmpty())
			throw new NoSuchElementException("No Products Found");
		return products;
	}

	public List<Product> getAllProductByName(String name, int page, int size, String sort, boolean desc) {

		List<Product> products = productRepository
				.findByApprovedTrueAndNameContaining(name,
						PageRequest.of(page - 1, size, desc ? Sort.by(sort).descending() : Sort.by(sort).ascending()))
				.getContent();
		if (products.isEmpty())
			throw new NoSuchElementException("No Products Found with Name: " + name);
		return products;
	}

	public List<Product> getAllProductByCategory(String category, int page, int size, String sort, boolean desc) {
		List<Product> products = productRepository
				.findByApprovedTrueAndCategory(category,
						PageRequest.of(page - 1, size, desc ? Sort.by(sort).descending() : Sort.by(sort).ascending()))
				.getContent();
		if (products.isEmpty())
			throw new NoSuchElementException("No Products Found with Category: " + category);
		return products;
	}

	public List<Product> getAllProductByPrice(double lowerRange, double higherRange, int page, int size, String sort,
			boolean desc) {
		List<Product> products = productRepository
				.findByPriceBetweenAndApprovedTrue(lowerRange, higherRange,
						PageRequest.of(page - 1, size, desc ? Sort.by(sort).descending() : Sort.by(sort).ascending()))
				.getContent();
		if (products.isEmpty())
			throw new NoSuchElementException(
					"No Products Found within Price range : " + lowerRange + " and " + higherRange);
		return products;
	}
	
	

	public void saveItem(Item item) {
		itemRepository.save(item);
	}

	public void deleteItem(Item item) {
		itemRepository.deleteById(item.getId());
	}
	
	public void deleteItems(List<Item> items) {
		for(Item item:items)
			deleteItem(item);
	}
	
	
	public void saveAll(List<Product> products) {
		productRepository.saveAll(products);
	}
}
