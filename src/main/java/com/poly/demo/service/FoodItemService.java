package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.FoodItem;
import com.poly.demo.repository.FoodItemRepository;

@Service
public class FoodItemService {

	@Autowired
	private FoodItemRepository foodItemRepository;

	public List<FoodItem> getAllFoodItems() {
		return foodItemRepository.findAll();
	}

	public Page<FoodItem> getFoodItems(Pageable pageable) {
		return foodItemRepository.findAll(pageable); // Giả sử bạn đã có repository cho FoodItem
	}

	// Lấy món ăn theo foodId
	public Optional<FoodItem> getFoodItemById(Integer foodItemId) {
		return foodItemRepository.findById(foodItemId);
	}

	// Lưu món ăn (thêm mới hoặc cập nhật)
	public FoodItem save(FoodItem foodItem) {
		return foodItemRepository.save(foodItem);
	}

	// Xóa món ăn theo ID
	public void delete(Integer id) {
		foodItemRepository.deleteById(id);
	}
}
