package com.jsp.ecommerce.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jsp.ecommerce.dao.ProductDao;
import com.jsp.ecommerce.entity.Product;
import com.jsp.ecommerce.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	

	private final ProductDao productDao;
	private final ProductMapper productMapper;

	@Override
	public Map<String, Object> getProducts(int page, int size, String sort, boolean desc, String name, String category,
			String lowerRange, String higherRange) {
		List<Product> products=new ArrayList<Product>();
		if(name==null && category==null && lowerRange==null && higherRange==null) {
			products=productDao.getAllApprovedProducts(page,size,sort,desc);
		}else {
			Set<Product> set=new HashSet<Product>();
			if(name!=null)
				set.addAll(productDao.getAllProductByName(name,page,size,sort,desc));
			if(category!=null)
				set.addAll(productDao.getAllProductByCategory(category, page, size, sort, desc));
			if(lowerRange!=null && higherRange!=null)
				set.addAll(productDao.getAllProductByPrice(Double.parseDouble(lowerRange), Double.parseDouble(higherRange), page, size, sort, desc));
		
			products.addAll(set);
		}
		if(products==null)
			throw new NoSuchElementException("No Products Found .");
		return Map.of("message","Products Found","products",productMapper.toProductDtoList(products));
	}

}
