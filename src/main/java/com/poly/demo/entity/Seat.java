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

    
    @Column(name = "seat_type")
    private String seatType;

    private Integer price;

    private String status;

    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}