package com.jsp.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jsp.ecommerce.dto.CustomerDto;
import com.jsp.ecommerce.dto.MerchantDto;
import com.jsp.ecommerce.dto.UserDto;
import com.jsp.ecommerce.entity.Customer;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.User;


@Mapper(componentModel = "spring")
public abstract class UserMapper {


	@Autowired
	PasswordEncoder passwordEncoder;

	@Mapping(target = "name", source = "username")
	public abstract UserDto toUserDto(User user);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", source = "name")
	@Mapping(target = "role", expression = "java(com.jsp.ecommerce.Enum.UserRole.MERCHANT)")
	@Mapping(target = "active", expression = "java(true)")
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(merchantDto.getPassword()))")
	public abstract User toUserEntity(MerchantDto merchantDto);
	
	

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user",source = "user")
	public abstract Merchant toMerchantEntity(MerchantDto merchantDto, User user);
	

	@Mapping(target = "password", constant = "**********")
	@Mapping(target = "email", expression = "java(merchant.getUser().getEmail())")
	@Mapping(target = "mobile", expression = "java(merchant.getUser().getMobile())")
	@Mapping(target = "id", expression = "java((long)(int)merchant.getUser().getId())")
	@Mapping(target = "status", expression = "java(merchant.getUser().isActive()?\"Active\":\"BLOCKED\")")
	public abstract MerchantDto toMerchantDto(Merchant merchant);
	
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", source = "name")
	@Mapping(target = "role", expression = "java(com.jsp.ecommerce.Enum.UserRole.USER)")
	@Mapping(target = "active", expression = "java(true)")
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(customerDto.getPassword()))")
	public abstract User toUserEntity(CustomerDto customerDto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", source = "user")
	public abstract Customer toCustomerEntity(CustomerDto customerDto, User user);

	@Mapping(target = "password", constant = "**********")
	@Mapping(target = "email", expression = "java(customer.getUser().getEmail())")
	@Mapping(target = "mobile", expression = "java(customer.getUser().getMobile())")
	@Mapping(target = "id", expression = "java((long)(int)customer.getUser().getId())")
	@Mapping(target = "status", expression = "java(customer.getUser().isActive()?\"Active\":\"BLOCKED\")")
	public abstract CustomerDto toCustomerDto(Customer customer);

	public abstract List<MerchantDto> toMerchantDtoList(List<Merchant> merchants);

	public abstract List<CustomerDto> toCustomerDtoList(List<Customer> customers);

}
