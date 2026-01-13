package com.jsp.ecommerce.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jsp.ecommerce.dao.UserDao;
import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.User;
import com.jsp.ecommerce.mapper.UserMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	
	private final UserDao userDao;
	private final UserMapper userMapper;

	@Override
	public Map<String, Object> getAllMerchants() {
		List<Merchant> merchants = userDao.getAllMerchants();
		return Map.of("message", "Merchant Records Found", "merchants", userMapper.toMerchantDtoList(merchants));
	}

	@Override
	public Map<String, Object> getAllCustomers() {
		List<Customer> customers = userDao.getAllCustomers();
		return Map.of("message", "Customer Records Found", "customers", userMapper.toCustomerDtoList(customers));
	}
	
	
	@Override
	public Map<String, Object> blockUser(Integer id) {
		User user = userDao.findById(id);
		user.setActive(false);
		userDao.save(user);
		return Map.of("message","Blocked Success","user",userMapper.toUserDto(user));
	}

	@Override
	public Map<String, Object> unblockUser(Integer id) {
		User user = userDao.findById(id);
		user.setActive(true);
		userDao.save(user);
		return Map.of("message","Un-Blocked Success","user",userMapper.toUserDto(user));
	}

}
