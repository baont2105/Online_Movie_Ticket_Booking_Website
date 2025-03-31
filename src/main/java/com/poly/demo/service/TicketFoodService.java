package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.FoodItem;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketFoodId;
import com.poly.demo.repository.TicketFoodRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketFoodService {

	@Autowired
	private TicketFoodRepository ticketFoodRepository;

	// Lưu thông tin TicketFood
	@Transactional
	public TicketFood saveTicketFood(Integer ticketId, Integer foodId, Integer quantity) {
		if (ticketId == null || foodId == null || quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("Dữ liệu không hợp lệ!");
		}

		// Tạo khóa chính
		TicketFoodId ticketFoodId = new TicketFoodId(ticketId, foodId);

		// Kiểm tra xem món ăn này đã tồn tại trong vé chưa
		Optional<TicketFood> existingTicketFood = ticketFoodRepository.findById(ticketFoodId);

		TicketFood ticketFood;
		if (existingTicketFood.isPresent()) {
			// Nếu đã tồn tại, chỉ cập nhật số lượng
			ticketFood = existingTicketFood.get();
			ticketFood.setQuantity(ticketFood.getQuantity() + quantity);
		} else {
			// Nếu chưa tồn tại, tạo mới
			Ticket ticket = new Ticket();
			ticket.setTicketId(ticketId);

			FoodItem foodItem = new FoodItem();
			foodItem.setFoodItemId(foodId);

			ticketFood = new TicketFood(ticketFoodId, ticket, foodItem, quantity);
		}

		return ticketFoodRepository.save(ticketFood);
	}

	// Lấy danh sách món ăn theo ticketId
	public List<TicketFood> getFoodItemsByTicketId(Integer ticketId) {
		return ticketFoodRepository.findByTicket_TicketId(ticketId);
	}

	// Lưu nhiều TicketFood cùng lúc
	@Transactional
	public void saveAllTicketFoods(List<TicketFood> ticketFoods) {
		if (ticketFoods == null || ticketFoods.isEmpty()) {
			throw new IllegalArgumentException("Danh sách trống!");
		}

		for (TicketFood tf : ticketFoods) {
			if (tf.getId() == null || tf.getQuantity() <= 0) {
				throw new IllegalArgumentException("Dữ liệu TicketFood không hợp lệ!");
			}
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

	public void deleteByTicketId(Integer ticketId) {
		ticketFoodRepository.deleteByTicketId(ticketId);
	}
}
