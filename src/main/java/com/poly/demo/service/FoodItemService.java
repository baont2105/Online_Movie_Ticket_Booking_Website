package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	// Lấy món ăn theo foodId
	public Optional<FoodItem> getFoodItemById(Integer foodId) {
		return foodItemRepository.findById(foodId);
	}
}
