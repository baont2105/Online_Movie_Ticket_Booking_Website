package com.poly.demo.service;

import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;
import com.poly.demo.repository.TicketFoodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketFoodService {

    @Autowired
    private TicketFoodRepository ticketFoodRepository;

    // Lưu thông tin TicketFood
    @Transactional
    public TicketFood saveTicketFood(TicketFood ticketFood) {
        if (ticketFood == null || ticketFood.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid TicketFood data!");
        }
        return ticketFoodRepository.save(ticketFood);
    }

    // Lưu nhiều TicketFood cùng lúc
    @Transactional
    public void saveAllTicketFoods(List<TicketFood> ticketFoods) {
        if (ticketFoods == null || ticketFoods.isEmpty()) {
            throw new IllegalArgumentException("Empty TicketFood list!");
        }
        ticketFoodRepository.saveAll(ticketFoods);
    }

    // Lấy tất cả TicketFood
    public List<TicketFood> getAllTicketFoods() {
        return ticketFoodRepository.findAll();
    }

    // Tìm TicketFood theo ID
    public Optional<TicketFood> getTicketFoodById(TicketFoodId id) {
        return ticketFoodRepository.findById(id);
    }

    // Lấy danh sách TicketFood theo vé
    public List<TicketFood> getFoodsByTicket(Ticket ticket) {
        return ticketFoodRepository.findByTicket(ticket);
    }

    // Xóa một TicketFood theo ID
    @Transactional
    public void deleteTicketFood(TicketFoodId id) {
        ticketFoodRepository.deleteById(id);
    }
}
