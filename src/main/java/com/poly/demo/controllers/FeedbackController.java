package com.poly.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.User;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private UserService userService;

	// Hiển thị form phản hồi
	@RequestMapping
	public String feedback(Model model) {
		addUserInfoToModel(model);
		return "home/feedback"; // Tên file Thymeleaf cho form phản hồi
	}

	// Xử lý gửi phản hồi
	@PostMapping("/send")
	public String sendFeedback(@RequestParam String name, @RequestParam String email, @RequestParam String description,
			RedirectAttributes redirectAttributes) {

		try {
			// Tạo email
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo("baontps29814@fpt.edu.vn"); // Địa chỉ email nhận phản hồi
			message.setSubject("Phản hồi từ người dùng: " + name);
			message.setText("Tên: " + name + "\nEmail: " + email + "\nMô tả vấn đề: " + description);

			// Gửi email
			mailSender.send(message);

			redirectAttributes.addFlashAttribute("successMessage", "Phản hồi đã được gửi thành công!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi gửi phản hồi!");
		}

		return "redirect:/feedback"; // Quay lại trang phản hồi
	}

	// Phương thức để thêm thông tin người dùng vào model
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
}
