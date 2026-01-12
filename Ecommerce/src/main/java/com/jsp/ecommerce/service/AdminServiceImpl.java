package com.jsp.ecommerce.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jsp.ecommerce.dao.UserDao;
import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Merchant;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	
	private final UserDao userDao;

	@Override
	public Map<String, Object> getAllMerchants() {
		List<Merchant> merchants = userDao.getAllMerchants();
		return Map.of("message", "Merchant Records Found", "merchants", merchants);
	}

	@Override
	public Map<String, Object> getAllCustomers() {
		List<Customer> customers = userDao.getAllCustomers();
		return Map.of("message", "Customer Records Found", "customers", customers);
	}

}
