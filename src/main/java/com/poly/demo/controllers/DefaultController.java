package com.poly.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.demo.entity.User;
import com.poly.demo.entity.Voucher;
import com.poly.demo.service.UserService;
import com.poly.demo.service.VoucherService;

@Controller
public class DefaultController {

	@Autowired
	private UserService userService;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

	@RequestMapping("/")
	public String index(Model model) {
		addUserInfoToModel(model);
		return "home/home";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		addUserInfoToModel(model);
		return "home/home";
	}

	@RequestMapping("/promotions")
	public String promotions(Model model) {
		addUserInfoToModel(model);

		List<Voucher> vouchers = voucherService.getAllVouchers();
		model.addAttribute("vouchers", vouchers);

		return "home/promotions";
	}

	// Quên mật khẩu
	@RequestMapping("/forgot-password")
	public String forgotPassword(Model model) {
		addUserInfoToModel(model);
		return "account/forgot-password"; // Trả về trang quên mật khẩu
	}

	@PostMapping("/forgot-password")
	public String resetPassword(@RequestParam String username, @RequestParam String newPassword,
			@RequestParam String confirmNewPassword, Model model) {
		// Kiểm tra tên đăng nhập
		Optional<User> optionalUser = userService.findByUsername(username);
		if (optionalUser.isEmpty()) {
			model.addAttribute("error_form", "Tên đăng nhập không tồn tại.");
			return "account/forgot-password"; // Trả về trang nhập lại
		}
		User user = optionalUser.get();

		// Kiểm tra mật khẩu mới và xác nhận mật khẩu
		if (!newPassword.equals(confirmNewPassword)) {
			model.addAttribute("error_form", "Mật khẩu mới và xác nhận không khớp.");
			return "account/forgot-password"; // Trả về trang nhập lại
		}

		// Mã hóa mật khẩu mới trước khi lưu
		user.setPassword(passwordEncoder.encode(newPassword));
		userService.save(user); // Cập nhật thông tin người dùng

		model.addAttribute("success", "Đã đặt lại mật khẩu thành công!");
		return "account/forgot-password"; // Trả về trang xác nhận thành công
	}

}
