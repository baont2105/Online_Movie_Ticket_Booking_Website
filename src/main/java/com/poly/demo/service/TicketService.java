package com.poly.demo.service;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Showtime;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketFood;
import com.poly.demo.entity.TicketVoucher;
import com.poly.demo.entity.User;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.TicketFoodRepository;
import com.poly.demo.repository.TicketRepository;
import com.poly.demo.repository.TicketVoucherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    
    public Page<Ticket> getTickets(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ticketId").ascending());
        return ticketRepository.findAll(pageable);
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
    
    //Ticket Food và Ticket Voucher
    @Autowired
    private TicketFoodRepository ticketFoodRepository;

    @Autowired
    private TicketVoucherRepository ticketVoucherRepository;

    public List<TicketFood> getFoodItemsByTicketId(Integer ticketId) {
        return ticketFoodRepository.findByTicketTicketId(ticketId);
    }

    public List<TicketVoucher> getVouchersByTicketId(Integer ticketId) {
        return ticketVoucherRepository.findByTicketTicketId(ticketId);
    }

}
