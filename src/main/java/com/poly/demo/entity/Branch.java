package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Branches")
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id")
	private Integer branchId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@Column(name = "phone_number")
	private String phoneNumber;
	private String email;
	private String city;
	private Boolean visible = true;

	// Getters & Setters
}
