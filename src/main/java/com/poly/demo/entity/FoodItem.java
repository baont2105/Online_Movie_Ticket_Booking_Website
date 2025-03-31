package com.poly.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "FoodItems")
public class FoodItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "food_id")
	private Integer foodId;

	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	private String image;
	private Boolean visible = true;

	// Getters & Setters
}
