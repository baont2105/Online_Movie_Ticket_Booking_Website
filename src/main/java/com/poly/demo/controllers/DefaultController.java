package com.poly.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

	private void addUserInfoToModel(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			String username = userDetails.getUsername(); // Lấy username từ UserDetails

			// Lấy thông tin User từ database
			Optional<User> userOptional = userService.findByUsername(username);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				model.addAttribute("user", user);
				model.addAttribute("name", user.getName()); // Gửi tên đến Thymeleaf
				return;
			}
		}

		// Nếu không tìm thấy user hoặc chưa đăng nhập
		model.addAttribute("user", null);
		model.addAttribute("name", null);
	}

	@RequestMapping("/")
	public String index(Model model) {
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

	@RequestMapping("/feedback")
	public String feedback(Model model) {
		addUserInfoToModel(model);
		return "home/feedback";
	}

	@RequestMapping("/forgot-password")
	public String forgotPassword(Model model) {
		addUserInfoToModel(model);
		return "account/forgot-password";
	}
}
