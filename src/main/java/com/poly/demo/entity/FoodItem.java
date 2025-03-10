package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "FoodItems")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Integer foodId;

    private String name;
    private String description;
    private Integer price;
    private String image;
    private Boolean visible;
}