package com.poly.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Seat;
import com.poly.demo.entity.TicketSeat;
import com.poly.demo.repository.TicketSeatRepository;

@Service
public class TicketSeatService {

	@Autowired
	private TicketSeatRepository ticketSeatRepository;

	// Lấy tất cả TicketSeat
	public List<TicketSeat> getAllTicketSeats() {
		return ticketSeatRepository.findAll();
	}

	// Lấy danh sách ghế theo ID vé
	public List<TicketSeat> getSeatsByTicketId(Integer ticketId) {
		return ticketSeatRepository.findByTicket_TicketId(ticketId);
	}

	// Lấy TicketSeat theo ID
	public Optional<TicketSeat> getTicketSeatById(Integer id) {
		return ticketSeatRepository.findById(id);
	}

	// Lưu danh sách TicketSeat
	public void saveAllTicketSeats(List<TicketSeat> ticketSeats) {
		ticketSeatRepository.saveAll(ticketSeats);
	}

	// Xóa một TicketSeat
	public void deleteTicketSeat(Integer id) {
		ticketSeatRepository.deleteById(id);
	}

	// Xóa tất cả TicketSeat của một vé
	public void deleteSeatsByTicketId(Integer ticketId) {
		ticketSeatRepository.deleteByTicket_TicketId(ticketId);
	}

	// Lấy danh sách ghế đã được đặt theo suất chiếu
	public List<Seat> getBookedSeatsByShowtime(Integer showtimeId) {
		List<TicketSeat> ticketSeats = ticketSeatRepository.findByTicket_Showtime_ShowtimeId(showtimeId);
		return ticketSeats.stream().map(TicketSeat::getSeat).collect(Collectors.toList());
	}
}
