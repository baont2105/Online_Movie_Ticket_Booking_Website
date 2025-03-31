package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ticket_Foods")
public class TicketFood {
	@EmbeddedId
	private TicketFoodId id;

	@ManyToOne
	@MapsId("ticketId")
	@JoinColumn(name = "ticket_id", insertable = false, updatable = false)
	private Ticket ticket;

	@ManyToOne
	@MapsId("foodId")
	@JoinColumn(name = "food_id", insertable = false, updatable = false)
	private FoodItem foodItem;

	@Column(nullable = false)
	private Integer quantity;
}
