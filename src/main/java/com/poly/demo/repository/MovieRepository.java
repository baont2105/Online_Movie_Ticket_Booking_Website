package com.poly.demo.repository;

import com.poly.demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByNameContaining(String name);
    List<Movie> findByCountry(String country);
}
