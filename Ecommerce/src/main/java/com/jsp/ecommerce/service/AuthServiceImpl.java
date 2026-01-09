package com.jsp.ecommerce.service;

import java.security.SecureRandom;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce.Enum.UserRole;
import com.jsp.ecommerce.dao.UserDao;
import com.jsp.ecommerce.dto.MerchantDto;
import com.jsp.ecommerce.dto.OtpDto;
import com.jsp.ecommerce.entity.Merchant;
import com.jsp.ecommerce.entity.User;
import com.jsp.ecommerce.security.JwtService;
import com.jsp.ecommerce.util.EmailService;
import com.jsp.ecommerce.util.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final RedisService redisService;

	public Map<String, Object> login(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		String token = jwtService.generateToken(userDetails);
		return Map.of("message", "Login Success", "token", token);
	}
	
	@Override
	public Map<String, Object> viewUser(String email) {
		User user = userDao.findByEmail(email);
		return Map.of("message", "Data Found", "user", user);
	}

	@Override
	public Map<String, Object> updatePassword(String email, String oldPassword, String newPassword) {
		User user = userDao.findByEmail(email);
		if (passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userDao.save(user);
			return Map.of("message", "Password Updated Success", "user", user);
		}
		throw new IllegalArgumentException("Old Password Not Matching");
	}

	@Override
	public Map<String, Object> registerMerchant(MerchantDto merchantDto) {
		if (userDao.checkEmailAndMobieDuplicate(merchantDto.getEmail(), merchantDto.getMobile()))
			throw new IllegalArgumentException("Already Account Exists with Email or Mobile");
		MerchantDto tempData = redisService.getTempData(merchantDto.getEmail());
		if (tempData != null)
			throw new IllegalArgumentException("Already Otp Sent First Verify It or After 30 minutes Try Again");
		Integer otp = generateOtp();
		emailService.sendOtpEmail(otp, merchantDto.getName(), merchantDto.getEmail());
		redisService.saveOtp(otp, merchantDto.getEmail());
		redisService.saveTempData(merchantDto, merchantDto.getEmail());
		return Map.of("message", "Otp Sent Succes Verify within 5 minutes");
	}

	private Integer generateOtp() {
		return new SecureRandom().nextInt(100000, 1000000);
	}

	@Override
	public Map<String, Object> verifyOtp(OtpDto dto) {
		Integer storedOtp = redisService.getOtp(dto.getEmail());
		MerchantDto merchantDto = redisService.getTempData(dto.getEmail());
		if (merchantDto == null)
			throw new IllegalArgumentException("No Account Exists recreate account");
		if (storedOtp == null)
			throw new IllegalArgumentException("Otp Expired, Try Resending");
		if (storedOtp.equals(dto.getOtp())) {
			User user = new User(null, merchantDto.getName(), merchantDto.getEmail(), passwordEncoder.encode(merchantDto.getPassword()), merchantDto.getMobile(), UserRole.MERCHANT, true);
//			
			userDao.save(user);
			Merchant merchant = new Merchant(null, merchantDto.getName(), merchantDto.getAddress(),
					merchantDto.getGstNo(), user);
			userDao.save(merchant);
			return Map.of("message", "Account Created Success", "user", merchant);
		} else {
			throw new IllegalArgumentException("Otp Missmatch Try Again");
		}

	}

	@Override
	public Map<String, Object> resendOtp(String email) {
		MerchantDto merchantDto = redisService.getTempData(email);
		if (merchantDto == null)
			throw new IllegalArgumentException("No Account Exists recreate account");
		int otp = generateOtp();
		emailService.sendOtpEmail(otp, merchantDto.getName(), merchantDto.getEmail());
		redisService.saveOtp(otp, merchantDto.getEmail());
		return Map.of("message", "OTP Resent Success");
	}
}
