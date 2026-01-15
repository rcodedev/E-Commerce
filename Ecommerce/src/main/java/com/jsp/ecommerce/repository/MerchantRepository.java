package com.jsp.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.User;

public interface MerchantRepository extends JpaRepository<Merchant, Long>{

	Optional<Merchant> findByUser(User user);
}
