package com.poly.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	// Lấy toàn bộ (không phân trang)
	List<Category> findAll();

	// Lấy theo phân trang
	@Query("SELECT c FROM Category c")
	Page<Category> findAllPage(Pageable pageable);

	void deleteByCategoryId(Integer id);
}
