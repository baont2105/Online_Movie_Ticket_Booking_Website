package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TicketFoodId implements Serializable {
	@Column(name = "ticket_id")
    private Integer ticketId;
	
	@Column(name = "food_id")
    private Integer foodItemId;
	
	 // Constructors
    public TicketFoodId() {}

    public TicketFoodId(Integer ticketId, Integer foodItemId) {
        this.ticketId = ticketId;
        this.foodItemId = foodItemId;
    }

    // Getters and Setters
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketFoodId that = (TicketFoodId) o;
        return Objects.equals(ticketId, that.ticketId) &&
               Objects.equals(foodItemId, that.foodItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, foodItemId);
    }
}