package com.poly.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "Showtimes")
@Data
public class Showtime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "showtime_id")
	private Integer showtimeId;

	@Column(nullable = false, name = "show_date")
	private LocalDate showDate;

	@Column(nullable = false, name = "start_time")
	private LocalTime startTime;

	@Column(nullable = false, name = "end_time")
	private LocalTime endTime;

	@Column(nullable = false)
	private Integer price;

	private Boolean visible = true;

	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	// Getters & Setters
}
