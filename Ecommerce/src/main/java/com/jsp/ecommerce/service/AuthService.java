package com.jsp.ecommerce.service;

import java.util.Map;

import com.jsp.ecommerce.dto.LoginDto;

public interface AuthService {

	Map<String, Object> login(LoginDto loginDto);
}
