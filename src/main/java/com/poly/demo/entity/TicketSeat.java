package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "Ticket_Seats", uniqueConstraints = @UniqueConstraint(columnNames = { "ticket_id", "seat_id" }))
public class TicketSeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_seat_id")
	private Integer ticketSeatId;

	@ManyToOne
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@ManyToOne
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;

	@Override
	public String toString() {
		return "TicketSeat{" + "seat=" + (seat != null ? seat.getSeatId() : "null") + ", ticketId="
				+ (ticket != null ? ticket.getTicketId() : "null") + '}';
	}

	// Getters & Setters
}
