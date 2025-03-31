package com.poly.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFoodId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ticket_id", nullable = false)
	private Integer ticketId;

	@Column(name = "food_id", nullable = false)
	private Integer foodId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TicketFoodId that = (TicketFoodId) o;
		return Objects.equals(ticketId, that.ticketId) && Objects.equals(foodId, that.foodId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticketId, foodId);
	}
}
