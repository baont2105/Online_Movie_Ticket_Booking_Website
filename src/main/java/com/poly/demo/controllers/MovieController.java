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

import com.poly.demo.entity.Category;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.User;
import com.poly.demo.service.CategoryService;
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

	@Autowired
	private CategoryService categoryService;

	private void addUserInfoToModel(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			String username = userDetails.getUsername();

			Optional<User> userOptional = userService.findByUsername(username);
			userOptional.ifPresentOrElse(user -> {
				model.addAttribute("user", user);
				model.addAttribute("name", user.getName());
			}, () -> {
				model.addAttribute("user", null);
				model.addAttribute("name", null);
			});
		}
		// Xử lý trường hợp đăng nhập bằng OAuth2 (mạng xã hội)
		else if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
			org.springframework.security.oauth2.core.user.DefaultOAuth2User oauth2User = (org.springframework.security.oauth2.core.user.DefaultOAuth2User) principal;

			String email = oauth2User.getAttribute("email");

			Optional<User> userOptional = userService.findByEmail(email);
			userOptional.ifPresentOrElse(user -> {
				model.addAttribute("user", user);
				model.addAttribute("name", user.getName());
			}, () -> {
				model.addAttribute("user", null);
				model.addAttribute("name", null);
			});
		} else {
			model.addAttribute("user", null);
			model.addAttribute("name", null);
		}
	}

	@GetMapping("/")
	public String listMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,
			Model model) {
		addUserInfoToModel(model);

		Page<Movie> moviePage = movieService.getMovies(page, size);

		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);

		model.addAttribute("movies", moviePage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", moviePage.getTotalPages());
		return "movie/movies-list"; // Tên file Thymeleaf (movies.html)
	}

	// =============== CHI TIẾT PHIM ================
	@GetMapping("/movie-detail/{id}")
	public String MovieDetail(@PathVariable Integer id, Model model) {
		addUserInfoToModel(model);

		Optional<Movie> movieOpt = movieService.getMovieById(id);
		if (movieOpt.isPresent()) {
			Movie movie = movieOpt.get();
			model.addAttribute("movie", movie);

			// Lấy phim cùng thể loại
			List<Movie> relatedMovies = movieService.findMoviesByCategory(movie.getCategory());
			model.addAttribute("relatedMovies", relatedMovies);

			// Lấy suất chiếu
			List<Showtime> showtime = showtimeService.getShowtimesByMovieId(id);
			model.addAttribute("showtime", showtime);

			return "movie/movie-detail";
		}
		return "redirect:/movies"; // Trả về danh sách phim nếu không tìm thấy
	}

	// =============== BỘ LỌC, TÌM KIẾM ================

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

	@GetMapping("/search")
	public String searchMovies(@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String keyword, Model model) {
		addUserInfoToModel(model);
		List<Category> categories = categoryService.getAllCategories();
		List<Movie> movies = movieService.searchMovies(categoryId, keyword);

		if (movies.isEmpty()) {
			model.addAttribute("error", "Không tìm thấy phim nào phù hợp.");
		}

		model.addAttribute("categories", categories);
		model.addAttribute("movies", movies);
		model.addAttribute("selectedCategory", categoryId);
		model.addAttribute("searchKeyword", keyword);
		return "movie/movies-list";
	}

}
