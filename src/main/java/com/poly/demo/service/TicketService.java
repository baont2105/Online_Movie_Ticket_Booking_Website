package com.poly.demo.service;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketByUserID(Integer userId) {
        return ticketRepository.findTicketsByUserId(userId);
    }

    public List<Ticket> getTicketsByShowtime(Showtime showtime) {
        return ticketRepository.findByShowtime(showtime);
    }
    
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public void updateTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }    

    public void deleteMovie(Long id) {
    	ticketRepository.deleteById(id);
    }
}
