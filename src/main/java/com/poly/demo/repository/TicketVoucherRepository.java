package com.poly.demo.repository;

import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;
import com.poly.demo.entity.TicketVoucher;
import com.poly.demo.entity.TicketVoucherId;
import com.poly.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketVoucherRepository extends JpaRepository<TicketVoucher, TicketVoucherId> {
    List<TicketVoucher> findByTicket(Ticket ticket);
}