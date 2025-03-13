package com.poly.demo.controllers;

import com.poly.demo.entity.Movie;
import com.poly.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Hiển thị danh sách phim trong trang quản lý
    @GetMapping("/manager")
    public String manageMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        System.out.print(movies);
        model.addAttribute("movies", movies);
        return "movies-manager";
    }

    // Hiển thị form thêm phim
    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie-form";
    }

    // Xử lý thêm phim
    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/movies/manager";
    }

    // Hiển thị form cập nhật phim
    @GetMapping("/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, Model model) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
            return "movie-form";
        }
        return "redirect:/movies/manager";
    }

    // Xử lý cập nhật phim
    @PostMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/movies/manager";
    }

    // Xóa phim
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies/manager";
    }
}
