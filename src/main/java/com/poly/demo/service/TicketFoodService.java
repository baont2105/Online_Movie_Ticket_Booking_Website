package com.poly.demo.service;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
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

	    public void saveTicketFood(TicketFood ticketFood) {
	        ticketFoodRepository.save(ticketFood);
	    }
}
