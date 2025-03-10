package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "Showtimes")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Integer showtimeId;

    @Column(name = "show_date")
    private LocalDate showDate;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    private Integer price;
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}