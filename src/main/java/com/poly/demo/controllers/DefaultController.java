package com.poly.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	@RequestMapping("/")
	public String index(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "home";
	}
	
	@RequestMapping("/ticketing/{id}")
	public String ticketing(@PathVariable Long id, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "ticketing";
	}

	@RequestMapping("/promotions")
	public String promotions(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "promotions";
	}

	@RequestMapping("/feedback")
	public String feedback(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "feedback";
	}

	@RequestMapping("/forgot-password")
	public String forgotPassword(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "forgot-pass";
	}

	@RequestMapping("/my-tickets")
	public String myTickets(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "my-tickets";
	}

	@RequestMapping("/account-information")
	public String accountInformation(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "account-information";
	}
}
