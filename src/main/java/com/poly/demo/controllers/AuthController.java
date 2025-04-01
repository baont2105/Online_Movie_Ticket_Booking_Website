package com.poly.demo.controllers;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.User;
import com.poly.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public AuthController(UserService userService, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}

	@PostMapping("/login")
	public String processLogin(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			return "redirect:/home";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
			return "redirect:/login?error";
		}
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "account/register";
	}

	@PostMapping("/register")
	public String processRegister(@ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "account/register";
		}

		// Kiểm tra username đã tồn tại chưa
		Optional<User> existingUser = userService.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			model.addAttribute("error", "Tên đăng nhập đã tồn tại.");
			return "account/register";
		}

		try {
			// Mã hóa mật khẩu trước khi lưu
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.create(user);
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "account/register";
		}

		return "redirect:/register?success";
	}

	@GetMapping("/check-login")
	@ResponseBody
	public String checkLogin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			return "User đang đăng nhập: " + user.getUsername();
		} else {
			return "Chưa có user nào đăng nhập!";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}
}
