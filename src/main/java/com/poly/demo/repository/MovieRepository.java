package com.poly.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Category;
import com.poly.demo.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	List<Movie> findByCountry(String country);

	Page<Movie> findAll(Pageable pageable);

	List<Movie> findByCategory(Category category);

	List<Movie> findByNameContaining(String keyword);

	@Query("SELECT m FROM Movie m WHERE m.releaseDate <= CURRENT_DATE AND m.endDate >= CURRENT_DATE")
	List<Movie> findNowShowingMovies();

	@Query("SELECT m FROM Movie m WHERE m.releaseDate > CURRENT_DATE")
	List<Movie> findUpcomingMovies();

	// ================= TÌM KIẾM ===================
	// Tìm kiếm phim theo tên (chứa từ khóa)
	List<Movie> findByNameContainingIgnoreCase(String keyword);

	// Tìm kiếm phim theo thể loại
	List<Movie> findByCategory_CategoryId(Integer categoryId);

	// Tìm kiếm phim theo cả thể loại và từ khóa
	List<Movie> findByCategory_CategoryIdAndNameContainingIgnoreCase(Integer categoryId, String keyword);
}
