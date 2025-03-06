package com.poly.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.demo.entity.Movie;
import com.poly.demo.service.MovieService;


@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/movies")
    public String listMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movies-list"; // Tên file Thymeleaf (movies.html)
    }
    
    @RequestMapping("/movie-detail/{id}")
    public String MovieDetail(@PathVariable Long id, Model model) {
    	Optional<Movie> movie = movieService.getMovieById(id);
    	model.addAttribute("movie", movie.get());
    	return "movie-detail";
    }
}
