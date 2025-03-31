package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Seats")
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_id")
	private Integer seatId;

	@Column(nullable = false, name = "row_number")
	private String rowNumber;

	@Column(nullable = false, name = "seat_number")
	private String seatNumber;

	@Column(nullable = false, name = "seat_type")
	private String seatType; // VIP, Standard

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private String status; // AVAILABLE, BOOKED, BLOCKED

	private Boolean visible = true;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	// Getters & Setters
}
