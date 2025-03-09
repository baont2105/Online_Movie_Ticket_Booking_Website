package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ticket_Foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketFood {
    @EmbeddedId
    private TicketFoodId id;

    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @MapsId("foodItemId")
    @JoinColumn(name = "food_item_id", nullable = false)
    private FoodItem foodItem;

    @Column(nullable = false)
    private int quantity;
}
