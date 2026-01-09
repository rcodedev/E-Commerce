package com.jsp.ecommerce.util;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.jsp.ecommerce.dto.MerchantDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisService {


	private final RedisTemplate<String, Object> redisTemplate;

	public void saveOtp(Integer otp, String email) {
		redisTemplate.opsForValue().set(email+"_otp", otp,Duration.ofMinutes(5));
	}

	public void saveTempData(MerchantDto merchantDto, String email) {
		redisTemplate.opsForValue().set(email+"_merchant", merchantDto,Duration.ofMinutes(30));
	}
	
	public Integer getOtp(String email) {
		return (Integer) redisTemplate.opsForValue().get(email+"_otp");
	}
	
	public MerchantDto getTempData(String email) {
		return (MerchantDto) redisTemplate.opsForValue().get(email+"_merchant");
	}
}
