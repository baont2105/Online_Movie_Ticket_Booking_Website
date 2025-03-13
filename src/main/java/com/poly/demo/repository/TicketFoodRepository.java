package com.poly.demo.repository;

import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketFoodRepository extends JpaRepository<TicketFood, TicketFoodId> {
    List<TicketFood> findByTicket(Ticket ticket); // Lấy danh sách đồ ăn theo vé

    List<TicketFood> findByTicketIn(List<Ticket> tickets); // Lấy danh sách đồ ăn theo nhiều vé
    
    List<TicketFood> findByTicketTicketId(Integer ticketId);
}
