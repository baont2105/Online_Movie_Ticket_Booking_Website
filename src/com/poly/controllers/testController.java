package com.poly.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/ticketing")
	public String ticketing() {
		return "ticketing";
	}

	@RequestMapping("/promotion")
	public String promotion() {
		return "promotion";
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

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/my-tickets")
	public String myTickets() {
		return "my-tickets";
	}
}
