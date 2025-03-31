package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Room;
import com.poly.demo.entity.Seat;
import com.poly.demo.repository.SeatRepository;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;

	public List<Seat> getSeatsByRoom(Room room) {
		return seatRepository.findByRoom(room);
	}

	public Optional<Seat> getSeatById(Integer seatId) { // 🔥 Sửa từ Long → Integer
		return seatRepository.findById(seatId);
	}

	public List<Seat> getSeatsByIds(List<Integer> seatIds) { // 🔥 Đổi từ List<String> → List<Integer>
		return seatRepository.findBySeatIdIn(seatIds);
	}

}
