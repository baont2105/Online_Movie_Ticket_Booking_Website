package com.poly.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
	Optional<FoodItem> findById(Integer id);
}