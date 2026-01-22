package com.jsp.ecommerce.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jsp.ecommerce.dao.ProductDao;
import com.jsp.ecommerce.dao.UserDao;
import com.jsp.ecommerce.dto.FakeStoreData;
import com.jsp.ecommerce.dto.ProductDto;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

	private final ProductDao productDao;
	private final UserDao userDao;
	private final ProductMapper productMapper;
	private final RestTemplate restTemplate;

	@Override
	public Map<String, Object> saveProduct(ProductDto productDto, String email) {
		Merchant merchant = userDao.getMerchantByEmail(email);
		Product product = productMapper.toProductEntity(productDto, merchant);
		productDao.save(product);
		return Map.of("message", "Product Added Success", "product", productMapper.toProductDto(product));
	}

	@Override
	public Map<String, Object> getProducts(String email) {
		Merchant merchant = userDao.getMerchantByEmail(email);
		List<Product> products = productDao.getMerchantProducts(merchant);
		return Map.of("message", "Products Found for User "+merchant.getName(), "products", productMapper.toProductDtoList(products));
	}

	@Override
	public Map<String, Object> deleteProduct(Long id, String email) {
		Merchant merchant = userDao.getMerchantByEmail(email);
		Product product = productDao.getProductByIdAndMerchant(id, merchant);
		productDao.delete(product);
		return Map.of("message", "Products Deleted Success", "product", productMapper.toProductDto(product));
	}

	@Override
	public Map<String, Object> updateProduct(Long id, ProductDto productDto, String email) {
		Merchant merchant = userDao.getMerchantByEmail(email);
		productDao.getProductByIdAndMerchant(id, merchant);
		Product product = productMapper.toProductEntity(productDto, merchant);
		product.setId(id);
		productDao.save(product);
		return Map.of("message", "Product Updated Success", "product", productMapper.toProductDto(product));
	}
	
	
	@Override
	public Map<String, Object> addProducts(String email) {
		Merchant merchant = userDao.getMerchantByEmail(email);

		FakeStoreData[] data = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreData[].class);
		List<FakeStoreData> productDtos = Arrays.asList(data);
		List<Product> products = new ArrayList<Product>();
		for (FakeStoreData storeData : productDtos) {
			Product product = productMapper.toProductEntity(storeData, merchant);
			products.add(product);
		}
		productDao.saveAll(products);
		return Map.of("Message", "Products Added Success", "products", productMapper.toProductDtoList(products));
	}
}
