package com.poly.demo.controllers;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import com.poly.demo.service.BranchService;
import com.poly.demo.service.MovieService;
import com.poly.demo.service.ShowtimeService;
import com.poly.demo.service.TicketService;
import com.poly.demo.service.UserService;

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

    @Autowired
    private UserService userService;

    @GetMapping("/accounts-manager")
    public String AccountsManager(Model model) {
        addUserInfoToModel(model);
        
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        
        return "accounts-manager";
    }
    @Autowired
    private ShowtimeService showtimeService;

    
    @GetMapping("/showtime-manager")
    public String ShowtimeManager(Model model) {
        addUserInfoToModel(model);
        
        List<Showtime> showtimes = showtimeService.getAllShowtime();
        model.addAttribute("showtimes", showtimes);
        
        return "showtime-manager";
    }
    
    @PostMapping("/showtime-manager/add")
    public String addShowtime(@ModelAttribute Showtime showtime) {
        showtimeService.addShowtime(showtime);
        return "redirect:/admin/showtime-manager";
    }
    
    @GetMapping("/showtime-manager/edit/{id}")
    public String showEditShowtimeForm(@PathVariable Long id, Model model) {
        addUserInfoToModel(model);
        
        Showtime showtime = showtimeService.getShowtimeById(id);
        if (showtime != null) {
            model.addAttribute("showtime", showtime);
            return "showtime-form"; // Trang chỉnh sửa suất chiếu
        }
        return "redirect:/admin/showtime-manager";
    }
    
    @PostMapping("/showtime-manager/edit/{id}")
    public String updateShowtime(@PathVariable Long id, @ModelAttribute Showtime showtime) {
        Showtime existingShowtime = showtimeService.getShowtimeById(id);
        if (existingShowtime != null) {
            existingShowtime.setMovie(showtime.getMovie());
            existingShowtime.setBranch(showtime.getBranch());
            existingShowtime.setStartTime(showtime.getStartTime());
            existingShowtime.setPrice(showtime.getPrice());

            showtimeService.addShowtime(existingShowtime);
        }
        return "redirect:/admin/showtime-manager";
    }
    
    @GetMapping("/showtime-manager/delete/{id}")
    public String deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return "redirect:/admin/showtime-manager";
    }
    
    @Autowired
    private BranchService branchService;

    @GetMapping("/branch-manager")
    public String BranchManager(Model model) {
        addUserInfoToModel(model);
        
        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        
        return "branch-manager";
        
    }
    @PostMapping("/branch-manager/add")
    public String addBranch(@ModelAttribute Branch branch) {
        branchService.addBranch(branch);
        return "redirect:/admin/branch-manager";
    }
    @GetMapping("/branch-manager/edit/{id}")
    public String showEditBranchForm(@PathVariable Long id, Model model) {
        Optional<Branch> branch = branchService.getBranchById(id);
        if (branch.isPresent()) {
            model.addAttribute("branch", branch.get());
            return "branch-form"; // Tạo thêm file Thymeleaf branch-form.html
        }
        return "redirect:/admin/branch-manager";
    }
    @GetMapping("/branch-manager/delete/{id}")
    public String deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return "redirect:/admin/branch-manager";
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
