package com.poly.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	// Lấy tất cả vé theo trang
	Page<Ticket> findAll(Pageable pageable);

	// Tìm vé theo ID người dùng
	@Query("SELECT t FROM Ticket t WHERE t.user.userId = :userId ORDER BY t.showtime.startTime DESC")
	List<Ticket> findTicketsByUserId(@Param("userId") Integer userId);

	// Tìm vé theo người dùng
	List<Ticket> findByUser(User user);

	// Tìm vé theo suất chiếu
	List<Ticket> findByShowtime(Showtime showtime);

	// Kiểm tra vé đã đặt cho một ghế cụ thể
	@Query("SELECT t FROM Ticket t JOIN t.ticketSeats ts WHERE t.user = :user AND ts.seat = :seat AND t.showtime = :showtime")
	Optional<Ticket> findByUserAndSeatAndShowtime(@Param("user") User user, @Param("seat") Seat seat,
			@Param("showtime") Showtime showtime);
}
