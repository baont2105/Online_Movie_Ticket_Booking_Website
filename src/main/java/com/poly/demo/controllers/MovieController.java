package com.poly.demo.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String listMovies(Model model) {
		addUserInfoToModel(model);
		
		List<Movie> movies = movieService.getAllMovies();
		 model.addAttribute("movies", movies);
		return "movies-list"; // Tên file Thymeleaf (movies.html)
	}

	@GetMapping("/now_showing")
	public String listMoviesNowShowing(Model model) {
		addUserInfoToModel(model);
 
		List<Movie> movies = movieService.getNowShowingMovies();
		model.addAttribute("movies", movies);

		return "movies-list"; // Tên file Thymeleaf (movies.html)
	}

	@GetMapping("/upcomming")
	public String listMoviesUpcomming(Model model) {
		addUserInfoToModel(model);

		List<Movie> movies = movieService.getUpcomingMovies();
		model.addAttribute("movies", movies);

		return "movies-list"; // Tên file Thymeleaf (movies.html)
	}

	/*
	 * 
	 * */
	@GetMapping("/movie-detail/{id}")
	public String MovieDetail(@PathVariable Long id, Model model) {
		addUserInfoToModel(model);

		Optional<Movie> movie = movieService.getMovieById(id);
		model.addAttribute("movie", movie.get());

		List<Showtime> showtime = showtimeService.getShowtimesByMovieId(id);
		model.addAttribute("showtime", showtime);

		return "movie-detail";
	}
}
