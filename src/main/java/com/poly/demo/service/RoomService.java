package com.poly.demo.service;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.RoomRepository;
import com.poly.demo.repository.SeatRepository;
import com.poly.demo.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private SeatRepository seatRepository;

	public List<Room> findAllRooms() {
		return roomRepository.findAll();
	}

	public Optional<Room> findRoomById(Integer id) {
		return roomRepository.findByRoomId(id);
	}

	public Room saveRoom(Room room) {
		addRoomWithSeats(room);
		return roomRepository.save(room);
	}

	public void deleteRoom(Integer id) {
		roomRepository.deleteByRoomId(id);
	}

	public Page<Room> getRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("roomId").ascending());
        return roomRepository.findAll(pageable);
    }

	public void addRoomWithSeats(Room room) {
		// Lưu phòng chiếu vào database
		Room savedRoom = roomRepository.save(room);

		// Tạo ghế tự động cho phòng chiếu
		createSeatsForRoom(savedRoom);
	}

	@Transactional
	private void createSeatsForRoom(Room room) {
		char[] rows = { 'A', 'B', 'C', 'D', 'E' };
		for (char row : rows) {
			for (int num = 1; num <= 10; num++) {
				Seat seat = new Seat();
				seat.setRoom(room);
				seat.setRowNumber(String.valueOf(row)); // Hàng ghế: "A", "B", "C"...
				seat.setSeatNumber(String.valueOf(num)); // Số ghế: "1", "2", "3"...
				seat.setSeatType(row == 'A' ? "VIP" : "Standard"); // Hàng A là ghế VIP
				seat.setPrice(row == 'A' ? 20000 : 0);
				seat.setStatus("AVAILABLE");
				seat.setVisible(true);
				System.out.println("CALLED!!!: " + seat);
				try {
				    seatRepository.save(seat);
				    seatRepository.flush();
				    System.out.println("Saved Seat ID: " + seat.getSeatId());
				} catch (Exception e) {
				    System.err.println("Lỗi khi lưu ghế: " + e.getMessage());
				}
			}
		}
	}
}
