package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Ticket_Foods")
public class TicketFood {
	@EmbeddedId
	private TicketFoodId id;

	@Column(nullable = false)
	private Integer quantity;

	@ManyToOne
	@MapsId("ticketId")
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@ManyToOne
	@MapsId("foodId")
	@JoinColumn(name = "food_id", nullable = false)
	private FoodItem foodItem;

	// Getters & Setters
}
