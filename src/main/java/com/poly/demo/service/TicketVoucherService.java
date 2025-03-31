package com.poly.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.TicketVoucher;
import com.poly.demo.repository.TicketVoucherRepository;

@Service
public class TicketVoucherService {
	@Autowired
	private TicketVoucherRepository ticketVoucherRepository;

	public List<TicketVoucher> getVouchersByTicket(Ticket ticket) {
		return ticketVoucherRepository.findByTicket(ticket);
	}

	public void save(TicketVoucher ticketVoucher) {
		ticketVoucherRepository.save(ticketVoucher);
	}
}
