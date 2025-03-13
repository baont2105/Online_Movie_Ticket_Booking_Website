package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Ticket_Foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"ticket", "foodItem"}) // Tránh lỗi vòng lặp
@Builder
public class TicketFood {

    @EmbeddedId
    private TicketFoodId id;

    @ManyToOne
    @MapsId("ticketId") // Khớp với ticketId trong TicketFoodId
    @JoinColumn(name = "ticket_id", nullable = false)
    @ToString.Exclude
    private Ticket ticket;

    @ManyToOne
    @MapsId("foodId") // Khớp với foodId trong TicketFoodId
    @JoinColumn(name = "food_id", nullable = false)
    @ToString.Exclude
    private FoodItem foodItem;

    @Column(nullable = false)
    private int quantity;

    // Constructor tiện dụng không cần truyền TicketFoodId
    public TicketFood(Ticket ticket, FoodItem foodItem, int quantity) {
        if (ticket == null || foodItem == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid TicketFood data!");
        }
        this.id = new TicketFoodId(ticket.getTicketId(), foodItem.getFoodItemId());
        this.ticket = ticket;
        this.foodItem = foodItem;
        this.quantity = quantity;
    }
}
