package com.poly.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.demo.entity.Movie;
import com.poly.demo.service.MovieService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping("/")
	public String AdminPage(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return ("dashboard");
	}

	@GetMapping("/accounts-manager")
	public String AccountsManager(Model model) {

		// Xác định user hiện tại
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return ("accounts-manager");
	}

	@GetMapping("/tickets-manager")
	public String TicketsManager(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return ("tickets-manager");
	}

	// MOVIE MANAGER
	@Autowired
	private MovieService movieService;

	@GetMapping("/movies-manager")
	public String MoviesManager(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		
		model.addAttribute("movie", new Movie());
		return ("movies-manager");
	}

	
	@PostMapping("/movie-manager/add")
	public String addMovie(@ModelAttribute Movie movie) {
		movieService.addMovie(movie);
		return "redirect:/movies-manager"; // Chuyển hướng về danh sách phim
	}
}
