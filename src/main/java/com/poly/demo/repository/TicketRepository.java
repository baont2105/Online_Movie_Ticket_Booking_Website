package com.poly.demo.repository;

import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);
    List<Ticket> findByShowtime(Showtime showtime);
    Optional<Ticket> findByUserAndSeatAndShowtime(User user, Seat seat, Showtime showtime);
}
