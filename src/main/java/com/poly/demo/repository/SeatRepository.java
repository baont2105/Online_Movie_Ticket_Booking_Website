package com.poly.demo.repository;

import com.poly.demo.entity.Room;
import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;
import com.poly.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByRoom(Room room);
    
    List<Seat> findBySeatIdIn(List<Long> seatIds);

    @Query("SELECT s FROM Seat s WHERE s.id NOT IN (SELECT t.seat.id FROM Ticket t WHERE t.showtime = :showtime)")
    List<Seat> findAvailableSeats(@Param("showtime") Showtime showtime);
}