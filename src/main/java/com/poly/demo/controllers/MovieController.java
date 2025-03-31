package com.poly.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.User;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.UserService;

@Controller
@RequestMapping("/movies")

public class MovieController {

	@Autowired
	private UserService userService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private ShowtimeService showtimeService;

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

	@GetMapping("/")
	public String listMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,
			Model model) {
		addUserInfoToModel(model);

		Page<Movie> moviePage = movieService.getMovies(page, size);
		model.addAttribute("movies", moviePage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", moviePage.getTotalPages());
		return "movie/movies-list"; // Tên file Thymeleaf (movies.html)
	}

	@GetMapping("/now_showing")
	public String listMoviesNowShowing(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size, Model model) {
		addUserInfoToModel(model);

		List<Movie> movies = movieService.getNowShowingMovies();
		Page<Movie> moviePage = movieService.getMovies(page, size);
		model.addAttribute("movies", movies);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", moviePage.getTotalPages());

		return "movie/movies-list"; // Tên file Thymeleaf (movies.html)
	}

	@GetMapping("/upcomming")
	public String listMoviesUpcomming(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size, Model model) {
		addUserInfoToModel(model);

		List<Movie> movies = movieService.getUpcomingMovies();
		Page<Movie> moviePage = movieService.getMovies(page, size);
		model.addAttribute("movies", movies);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", moviePage.getTotalPages());

		return "movie/movies-list"; // Tên file Thymeleaf (movies.html)
	}

	/*
	 * 
	 * */
	@GetMapping("/movie-detail/{id}")
	public String MovieDetail(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);

		Optional<Movie> movie = movieService.getMovieById(id);
		model.addAttribute("movie", movie.get());

		List<Showtime> showtime = showtimeService.getShowtimesByMovieId(id);
		model.addAttribute("showtime", showtime);

		return "movie/movie-detail";
	}
}
