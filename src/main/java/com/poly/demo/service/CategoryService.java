package com.poly.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Category;
import com.poly.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Page<Category> getCategories(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return categoryRepository.findAll(pageable);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
}
