package com.poly.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TicketFoodId implements Serializable {
	@Column(name = "ticket_id")
	private Integer ticketId;

	@Column(name = "food_id")
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
