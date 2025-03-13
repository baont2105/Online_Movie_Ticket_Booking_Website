package com.poly.demo.controllers;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Ticket;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MovieService movieService;

    // Thêm thông tin user vào model
    private void addUserInfoToModel(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            model.addAttribute("user", (UserDetails) principal);
        } else {
            model.addAttribute("user", null);
        }
    }

    @GetMapping("/")
    public String AdminPage(Model model) {
        addUserInfoToModel(model);
        return "dashboard";
    }

    @GetMapping("/accounts-manager")
    public String AccountsManager(Model model) {
        addUserInfoToModel(model);
        return "accounts-manager";
    }
    @GetMapping("/showtime-manager")
    public String ShowtimeManager(Model model) {
    	addUserInfoToModel(model);
    	return "showtime-manager";
    }
    @GetMapping("/branch-manager")
    public String BranchManager(Model model) {
    	addUserInfoToModel(model);
    	return "brach-manager";
    }

    @Autowired
    private TicketService ticketService;
    @GetMapping("/tickets-manager")
    public String TicketsManager(Model model) {
        addUserInfoToModel(model);
        
        List<Ticket> tickets = ticketService.getAllTicket();
        model.addAttribute("tickets", tickets);
        
        return "tickets-manager";
    }

    // MOVIE MANAGER - DANH SÁCH PHIM
    @GetMapping("/movies-manager")
    public String MoviesManager(Model model) {
        addUserInfoToModel(model);
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("movie", new Movie()); // Để dùng trong form thêm mới
        return "movies-manager";
    }

    // THÊM PHIM
    @PostMapping("/movies-manager/add")
    public String addMovie(@ModelAttribute Movie movie, Model model) {
        addUserInfoToModel(model);
        movie.setThumbnail("absolute_cinema.jpg"); // Ảnh mặc định nếu chưa có upload
        movieService.addMovie(movie);
        return "redirect:/admin/movies-manager"; // Chuyển hướng về trang danh sách phim
    }

    // CHỈNH SỬA PHIM - HIỂN THỊ FORM
    @GetMapping("/movies-manager/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, Model model) {
        addUserInfoToModel(model);
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
            return "movie-form"; // Trang chỉnh sửa phim
        }
        return "redirect:/admin/movies-manager";
    }

    // XỬ LÝ CẬP NHẬT PHIM
    @PostMapping("/movies-manager/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/admin/movies-manager";
    }

    // XÓA PHIM
    @GetMapping("/movies-manager/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/admin/movies-manager";
    }
}
