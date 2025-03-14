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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	public List<Room> findAllRooms() {
		return roomRepository.findAll();
	}

	public Optional<Room> findRoomById(Integer id) {
		return roomRepository.findByRoomId(id);
	}

	public Room saveRoom(Room room) {
		return roomRepository.save(room);
	}

	public void deleteRoom(Integer id) {
		roomRepository.deleteByRoomId(id);
	}
	    @Autowired
	    private SeatRepository seatRepository;

	    public void addRoomWithSeats(Room room) {
	        // Lưu phòng chiếu vào database
	        Room savedRoom = roomRepository.save(room);

	        // Tạo ghế tự động cho phòng chiếu
	        createSeatsForRoom(savedRoom);
	    }

	    private void createSeatsForRoom(Room room) {
	        char[] rows = {'A', 'B', 'C', 'D', 'E'};
	        for (char row : rows) {
	            for (int num = 1; num <= 10; num++) {
	                Seat seat = new Seat();
	                seat.setSeatNumber(row + String.valueOf(num));
	                seat.setRoom(room);
	                seat.setSeatType(row == 'A' ? "VIP" : "Regular"); // Hàng A là ghế VIP
	                seatRepository.save(seat);
	            }
	            }
	    }
}
