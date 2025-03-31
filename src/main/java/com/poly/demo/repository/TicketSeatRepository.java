package com.poly.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.TicketSeat;

@Repository
public interface TicketSeatRepository extends JpaRepository<TicketSeat, Integer> {

	// Tìm danh sách ghế theo ID vé
	List<TicketSeat> findByTicket_TicketId(Integer ticketId);

	// Tìm danh sách TicketSeat theo ID suất chiếu
	List<TicketSeat> findByTicket_Showtime_ShowtimeId(Integer showtimeId);

	// Xóa tất cả TicketSeat theo ID vé
	void deleteByTicket_TicketId(Integer ticketId);
}
