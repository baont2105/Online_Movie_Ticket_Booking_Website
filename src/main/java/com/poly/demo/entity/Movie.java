package com.poly.demo.entity;

import java.time.LocalDate;

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
@Table(name = "Movies")
@Data
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private Integer movieId;

	@Column(nullable = false)
	private String name;

	private String tags;
	private Integer duration;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "view_count")
	private Long viewCount = 0L;
	private String country;
	private String producer;
	private String director;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String actors;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	private String thumbnail;
	private String trailer;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	// Getters & Setters
}
