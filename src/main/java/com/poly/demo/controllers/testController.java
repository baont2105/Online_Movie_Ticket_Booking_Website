package com.poly.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.User;
import com.poly.demo.service.UserService;

@Controller
public class testController {

	@RequestMapping("/ticketing")
	public String ticketing() {
		return "ticketing";
	}

	@RequestMapping("/promotions")
	public String promotions() {
		return "promotions";
	}

	@RequestMapping("/movies-list")
	public String moviesList() {
		return "movies-list";
	}

	@RequestMapping("/movie-detail")
	public String movieDetail() {
		return "movie-detail";
	}

	@RequestMapping("/home")
	public String home() {
		return "home";
	}

	@RequestMapping("/feedback")
	public String feedback() {
		return "feedback";
	}

	@RequestMapping("/account-information")
	public String accountInformation() {
		return "account-information";
	}

	@RequestMapping("/forgot-password")
	public String forgotPassword() {
		return "forgot-pass";
	}

	@RequestMapping("/my-tickets")
	public String myTickets() {
		return "my-tickets";
	}
}
