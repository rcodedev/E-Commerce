package com.jsp.ecommerce.service;

import java.util.Map;

public interface AdminService {

	Map<String, Object> getAllMerchants();

	Map<String, Object> getAllCustomers();
	
	Map<String, Object> blockUser(Integer id);

	Map<String, Object> unblockUser(Integer id);

}
