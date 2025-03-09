package com.poly.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class TicketFoodId implements Serializable {
    private Integer ticketId;
    private Integer foodId;
}