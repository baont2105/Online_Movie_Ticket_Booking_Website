package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.repository.ShowtimeRepository;

@Service
public class ShowtimeService {

	@Autowired
	private ShowtimeRepository showtimeRepository;

	public List<Showtime> getAllShowtime() {
		return showtimeRepository.findAll();
	}

	public Optional<Showtime> getShowtimeById(Integer id) {
		return showtimeRepository.findById(id);
	}

	public boolean existsById(Integer id) {
		return showtimeRepository.existsById(id);
	}

	public List<Showtime> getShowtimesByMovieAndBranch(Movie movie, Branch branch) {
		return showtimeRepository.findByMovieAndBranch(Optional.ofNullable(movie), Optional.ofNullable(branch));
	}

	public List<Showtime> getShowtimesByMovieId(Integer movieId) {
		return showtimeRepository.findShowtimesByMovieId(movieId);
	}

	public List<Showtime> getShowtimesByMovie(Movie movie) {
		if (movie == null) {
			throw new IllegalArgumentException("Phim không được để trống!");
		}
		return showtimeRepository.findByMovie(movie);
	}

	public Showtime addShowtime(Showtime showtime) {
		return showtimeRepository.save(showtime);
	}

	public void deleteShowtime(Integer id) {
		if (!showtimeRepository.existsById(id)) {
			throw new IllegalArgumentException("Không tìm thấy suất chiếu có ID: " + id);
		}
		showtimeRepository.deleteById(id);
	}

	public Page<Showtime> getShowtimesPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("showtimeId").ascending());
		return showtimeRepository.findAll(pageable);
	}
}
