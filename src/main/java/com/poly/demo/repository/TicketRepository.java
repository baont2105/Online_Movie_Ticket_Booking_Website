package com.poly.demo.repository;

import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId ORDER BY t.showtime.startTime DESC")
    List<Ticket> findTicketsByUserId(@Param("userId") Integer userId);
    
    List<Ticket> findByShowtime(Showtime showtime);
    Optional<Ticket> findByUserAndSeatAndShowtime(User user, Seat seat, Showtime showtime);
}
