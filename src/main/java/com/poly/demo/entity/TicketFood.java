package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Ticket_Foods")
public class TicketFood {
    @EmbeddedId
    private TicketFoodId id;

    private Integer quantity;

    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "food_id")
    private FoodItem food;
}