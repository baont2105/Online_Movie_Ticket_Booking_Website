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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.ShowtimeService;

@Controller
@RequestMapping("/booking/")
@SessionAttributes("selectedShowtime")
public class BookingController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private ShowtimeService showtimeService;

	/*
	 * @GetMapping("/step1/{id}") public String step1(@PathVariable Long id, Model
	 * model) { Object principal =
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 * 
	 * if (principal instanceof UserDetails) { UserDetails user = (UserDetails)
	 * principal; model.addAttribute("user", user); // Gửi user đến Thymeleaf } else
	 * { model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null }
	 * return "booking_step1"; }
	 */

	@GetMapping("/step1")
	public String showStep1(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}

		model.addAttribute("movies", movieService.getAllMovies());
		model.addAttribute("branches", branchService.getAllBranches());
		return "booking_step1";
	}
	@GetMapping("/step1/{id}")
    public String showStep1(@PathVariable Long id, Model model) {
        addUserInfoToModel(model);

        Movie movie = movieService.getMovieById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        Branch branch = branchService.getBranchById(1L).orElseThrow(() -> new IllegalArgumentException("Invalid branch ID"));
        
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(Optional.of(movie), Optional.of(branch));

        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("selectedMovie", movie);
        model.addAttribute("selectedBranch", branch);
        model.addAttribute("showtimes", showtimes);
        
        return "booking_step1";
    }

    @PostMapping("/select-showtime")
    public String selectShowtime(@RequestParam Long movieId, @RequestParam Long branchId, Model model) {
        addUserInfoToModel(model);
        
        Movie movie = movieService.getMovieById(movieId).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        Branch branch = branchService.getBranchById(branchId).orElseThrow(() -> new IllegalArgumentException("Invalid branch ID"));
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieAndBranch(Optional.of(movie), Optional.of(branch));

        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("selectedMovie", movie);
        model.addAttribute("selectedBranch", branch);
        model.addAttribute("showtimes", showtimes);
        
        return "booking_step1";
    }

    @PostMapping("/confirm-showtime")
    public String confirmShowtime(@RequestParam Long showtimeId, RedirectAttributes redirectAttributes) {
        Showtime showtime = showtimeService.getShowtimeById(showtimeId);
        redirectAttributes.addFlashAttribute("selectedShowtime", showtime);
        return "redirect:/booking/step2";
    }

    private void addUserInfoToModel(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            model.addAttribute("user", (UserDetails) principal);
        } else {
            model.addAttribute("user", null);
        }
    }

	
	//STEP 2
	@GetMapping("/step2/{id}")
	public String step2(@PathVariable Long id, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "booking_step2";
	}

	@GetMapping("/step3/{id}")
	public String step3(@PathVariable Long id, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "booking_step3";
	}

	@GetMapping("/step4/{id}")
	public String step4(@PathVariable Long id, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			UserDetails user = (UserDetails) principal;
			model.addAttribute("user", user); // Gửi user đến Thymeleaf
		} else {
			model.addAttribute("user", null); // Nếu chưa đăng nhập, user sẽ là null
		}
		return "booking_step4";
	}
}
