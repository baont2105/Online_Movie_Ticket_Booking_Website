package com.poly.demo.controllers;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.User;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/account")
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	private void addUserInfoToModel(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			String username = userDetails.getUsername();

			Optional<User> userOptional = userService.findByUsername(username);
			userOptional.ifPresentOrElse(user -> {
				model.addAttribute("user", user);
				model.addAttribute("name", user.getName());
			}, () -> {
				model.addAttribute("user", null);
				model.addAttribute("name", null);
			});
		}
		// Xử lý trường hợp đăng nhập bằng OAuth2 (mạng xã hội)
		else if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
			org.springframework.security.oauth2.core.user.DefaultOAuth2User oauth2User = (org.springframework.security.oauth2.core.user.DefaultOAuth2User) principal;

			String email = oauth2User.getAttribute("email");

			Optional<User> userOptional = userService.findByEmail(email);
			userOptional.ifPresentOrElse(user -> {
				model.addAttribute("user", user);
				model.addAttribute("name", user.getName());
			}, () -> {
				model.addAttribute("user", null);
				model.addAttribute("name", null);
			});
		} else {
			model.addAttribute("user", null);
			model.addAttribute("name", null);
		}
	}

	// Lấy thông tin người dùng đăng nhập
	@GetMapping
	public String getAccountInfo(Model model, RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();

		Optional<User> userOptional = userService.findByUsername(email);
		if (userOptional.isPresent()) {
			model.addAttribute("user", userOptional.get());
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tài khoản");
		}
		return "account/account-info"; // Tên file Thymeleaf
	}

	// Cập nhật thông tin tài khoản
	@PostMapping("/update")
	public String updateAccount(@RequestParam("id") Integer id, @ModelAttribute User user,
			RedirectAttributes redirectAttributes) {

		Optional<User> userOptional = userService.getUserById(id);
		if (userOptional.isPresent()) {
			User existingUser = userOptional.get();
			existingUser.setName(user.getName());
			existingUser.setPhoneNumber(user.getPhoneNumber());

			userService.save(existingUser);
			redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tài khoản");
		}
		return "redirect:/account";
	}

	// Đổi mật khẩu
	@PostMapping("/change-password")
	public String changePassword(@RequestParam Integer userId, @RequestParam String newPassword,
			@RequestParam String confirmPassword, Model model, RedirectAttributes redirectAttributes) {
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
			return "redirect:/account";
		}

		Optional<User> userOptional = userService.getUserById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setPassword(passwordEncoder.encode(newPassword));
			userService.save(user);
			redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công!");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tài khoản!");
		}

		return "redirect:/account";
	}

}
