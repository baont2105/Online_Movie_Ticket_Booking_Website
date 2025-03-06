package com.poly.demo.service;

import com.poly.demo.entity.User;
import com.poly.demo.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa mật khẩu
		user.setName("anonymous");
		user.setPhoneNumber("0987654321");
		user.setVisible(true);
		user.setRole("USER"); // Mặc định là USER
		userRepository.save(user); // Lưu vào database
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}