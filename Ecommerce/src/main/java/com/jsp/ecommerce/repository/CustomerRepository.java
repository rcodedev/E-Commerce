package com.jsp.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByUser(User user);
}
