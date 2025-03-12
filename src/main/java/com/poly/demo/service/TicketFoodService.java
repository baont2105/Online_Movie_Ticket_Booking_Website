package com.poly.demo.service;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;
import com.poly.demo.entity.User;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.TicketFoodRepository;
import com.poly.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketFoodService {

	@Autowired
    private TicketFoodRepository ticketFoodRepository;

    // Lưu thông tin TicketFood (nếu chưa tồn tại)
    public void saveTicketFood(TicketFood ticketFood) {
        if (ticketFood != null) {
            ticketFoodRepository.save(ticketFood);
        }
    }

    // Lấy tất cả TicketFood
    public List<TicketFood> getAllTicketFoods() {
        return ticketFoodRepository.findAll();
    }

    // Tìm TicketFood theo ID
    public Optional<TicketFood> getTicketFoodById(TicketFoodId id) {
        return ticketFoodRepository.findById(id);
    }

    // Xóa một TicketFood theo ID
    public void deleteTicketFood(TicketFoodId id) {
        if (ticketFoodRepository.existsById(id)) {
            ticketFoodRepository.deleteById(id);
        }
    }
}
