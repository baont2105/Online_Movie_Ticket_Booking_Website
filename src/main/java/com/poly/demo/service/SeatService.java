package com.poly.demo.service;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Room;
import com.poly.demo.entity.Seat;
import com.poly.demo.entity.Showtime;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.SeatRepository;
import com.poly.demo.repository.ShowtimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService {

	@Autowired
    private SeatRepository seatRepository;

    public List<Seat> getSeatsByRoom(Room room) {
        return seatRepository.findByRoom(room);
    }

    public Optional<Seat> getSeatById(Long seatId) {
        return seatRepository.findById(seatId);
    }
    
    public List<Seat> getSeatsByIds(List<String> seatIds) {
        // Chuyển danh sách String thành danh sách Long
        List<Long> ids = seatIds.stream()
                                .map(Long::parseLong)
                                .collect(Collectors.toList());
        return seatRepository.findBySeatIdIn(ids);
    }
}
