package com.poly.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.demo.entity.User;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.UserService;



@Controller
public class DefaultController {
	
	@Autowired
    private UserService userService;
	
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
		return "home";
	}

	@RequestMapping("/promotions")
	public String promotions(Model model) {
		addUserInfoToModel(model);
		return "promotions";
	}

	@RequestMapping("/feedback")
	public String feedback(Model model) {
		addUserInfoToModel(model);
		return "feedback";
	}

	@RequestMapping("/forgot-password")
	public String forgotPassword(Model model) {
		addUserInfoToModel(model);
		return "forgot-pass";
	}
}
