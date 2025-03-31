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
@Table(name = "Vouchers")
public class Voucher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voucher_id")
	private Integer voucherId;

	@Column(unique = true, nullable = false)
	private String code;

	@Column(nullable = false, name = "discount_amount")
	private Integer discountAmount;

	// Getters & Setters
}
