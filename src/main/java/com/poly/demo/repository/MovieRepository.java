package com.poly.demo.repository;

import com.poly.demo.entity.Movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByNameContaining(String name);
    List<Movie> findByCountry(String country);
    Page<Movie> findAll(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.releaseDate <= CURRENT_DATE AND m.endDate >= CURRENT_DATE")
    List<Movie> findNowShowingMovies();
    @Query("SELECT m FROM Movie m WHERE m.releaseDate > CURRENT_DATE")
    List<Movie> findUpcomingMovies();
}
