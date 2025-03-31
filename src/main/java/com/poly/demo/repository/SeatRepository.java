package com.poly.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Room;
import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

	// Lấy danh sách ghế theo phòng
	List<Seat> findByRoom(Room room);

	// Lấy danh sách ghế theo danh sách seatId
	List<Seat> findBySeatIdIn(List<Integer> seatIds);

	// Lấy danh sách ghế theo trạng thái
	List<Seat> findByStatus(String status);

	// Lấy danh sách ghế còn trống cho một suất chiếu cụ thể
	@Query("SELECT s FROM Seat s WHERE s.room = :room AND s.seatId NOT IN "
			+ "(SELECT ts.seat.seatId FROM TicketSeat ts WHERE ts.ticket.showtime = :showtime)")
	List<Seat> findAvailableSeats(@Param("room") Room room, @Param("showtime") Showtime showtime);
}
