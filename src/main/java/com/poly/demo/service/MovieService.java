package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Movie;
import com.poly.demo.repository.MovieRepository;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	public List<Movie> getNowShowingMovies() {
		return movieRepository.findNowShowingMovies();
	}

	public List<Movie> getUpcomingMovies() {
		return movieRepository.findUpcomingMovies();
	}

	public Optional<Movie> getMovieById(Integer id) {
		return movieRepository.findById(id);
	}

	public List<Movie> searchMoviesByName(String name) {
		if (name == null || name.trim().isEmpty()) {
			return List.of(); // Trả về danh sách rỗng nếu tên phim không hợp lệ
		}
		return movieRepository.findByNameContaining(name);
	}

	public Movie addMovie(Movie movie) {
		if (movie == null || movie.getName() == null || movie.getName().trim().isEmpty()) {
			throw new IllegalArgumentException("Phim không hợp lệ!");
		}
		return movieRepository.save(movie);
	}

	public Page<Movie> getMovies(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("movieId").ascending());
		return movieRepository.findAll(pageable);
	}

	public Movie updateMovie(Integer id, Movie updatedMovie) {
		return movieRepository.findById(id).map(movie -> {
			movie.setName(updatedMovie.getName());
			movie.setTags(updatedMovie.getTags());
			movie.setDuration(updatedMovie.getDuration());
			movie.setReleaseDate(updatedMovie.getReleaseDate());
			movie.setEndDate(updatedMovie.getEndDate());
			movie.setViewCount(updatedMovie.getViewCount());
			movie.setCountry(updatedMovie.getCountry());
			movie.setProducer(updatedMovie.getProducer());
			movie.setDirector(updatedMovie.getDirector());
			movie.setActors(updatedMovie.getActors());
			movie.setDescription(updatedMovie.getDescription());
			movie.setThumbnail(updatedMovie.getThumbnail());
			movie.setTrailer(updatedMovie.getTrailer());
			return movieRepository.save(movie);
		}).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phim có ID: " + id));
	}

	public void deleteMovie(Integer id) {
		if (!movieRepository.existsById(id)) {
			throw new IllegalArgumentException("Không tìm thấy phim có ID: " + id);
		}
		movieRepository.deleteById(id);
	}
}
