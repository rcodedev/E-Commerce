package com.jsp.ecommerce.dao;

import org.springframework.stereotype.Repository;

import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.User;
import com.jsp.ecommerce.repository.CustomerRepository;
import com.jsp.ecommerce.repository.MerchantRepository;
import com.jsp.ecommerce.repository.UserRespository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDao {

	private final UserRespository userRepository;
	private final MerchantRepository merchantRepository;
	private final CustomerRepository customerRepository;

	public boolean checkEmailAndMobieDuplicate(String email, Long mobile) {
		return userRepository.existsByEmailOrMobile(email, mobile);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow();
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void save(Merchant merchant) {
		merchantRepository.save(merchant);
	}
	
	public void save(Customer customer) {
		customerRepository.save(customer);
	}
}
