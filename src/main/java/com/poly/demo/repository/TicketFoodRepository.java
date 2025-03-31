package com.poly.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;

import jakarta.transaction.Transactional;

@Repository
public interface TicketFoodRepository extends JpaRepository<TicketFood, TicketFoodId> {
	List<TicketFood> findByTicket(Ticket ticket); // Lấy danh sách đồ ăn theo vé

	List<TicketFood> findByTicketIn(List<Ticket> tickets); // Lấy danh sách đồ ăn theo nhiều vé

	List<TicketFood> findByTicket_TicketId(Integer ticketId);

	@Modifying
	@Transactional
	@Query("DELETE FROM TicketFood tf WHERE tf.ticket.ticketId = :ticketId")
	void deleteByTicketId(@Param("ticketId") Integer ticketId);
}
