package com.poly.demo.service;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.ShowtimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<Showtime> getAllShowtime() {
        return showtimeRepository.findAll();
    }
    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id).orElse(null);
    }
    
    public List<Showtime> getShowtimesByMovieAndBranch(Optional<Movie> movie, Optional<Branch> branch) {
        return showtimeRepository.findByMovieAndBranch(movie, branch);
    }

    public List<Showtime> getShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findShowtimesByMovieId(movieId);
    }

    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }
public void deleteShowtime(Long id) {
    	showtimeRepository.deleteById(id);
    }
}
