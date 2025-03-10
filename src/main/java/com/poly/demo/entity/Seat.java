package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer seatId;

    @Column(name = "row_number")
    private String rowNumber;
    
    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
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