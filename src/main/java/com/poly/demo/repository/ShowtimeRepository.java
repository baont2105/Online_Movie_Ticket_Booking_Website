package com.poly.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
	List<Showtime> findByMovieAndBranch(Optional<Movie> movie, Optional<Branch> branch);

	List<Showtime> findByMovie(Movie movie);

	List<Showtime> findByRoom(Room room);

	@Query("SELECT s FROM Showtime s WHERE s.movie.id = :movieId ORDER BY s.startTime ASC")
	List<Showtime> findShowtimesByMovieId(@Param("movieId") Integer movieId);
}