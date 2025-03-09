package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    private String rowNumber;
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public enum SeatType {
        VIP, STANDARD, DELUXE
    }

    public enum SeatStatus {
        AVAILABLE, BOOKED, BLOCKED
    }
}