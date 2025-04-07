package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poly.demo.entity.User;
import com.poly.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void create(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("Tên tài khoản đã tồn tại.");
		}
		user.setName("anonymous");
		user.setPhoneNumber("0987654321");
		user.setVisible(true);
		user.setRole("USER");
		userRepository.save(user);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void update(User user) {
		Optional<User> existingUser = userRepository.findById(user.getUserId());
		if (existingUser.isPresent()) {
			User updatedUser = existingUser.get();
			updatedUser.setName(user.getName());
			updatedUser.setEmail(user.getEmail());
			updatedUser.setPhoneNumber(user.getPhoneNumber());
			updatedUser.setRole(user.getRole());
			updatedUser.setVisible(user.getVisible());
			userRepository.save(updatedUser);
		} else {
			throw new RuntimeException("Người dùng không tồn tại!");
		}
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
