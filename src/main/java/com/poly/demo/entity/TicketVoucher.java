package com.poly.demo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Ticket_Vouchers")
public class TicketVoucher {
	@EmbeddedId
	private TicketVoucherId id;

	@ManyToOne
	@MapsId("ticketId")
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@ManyToOne
	@MapsId("voucherId")
	@JoinColumn(name = "voucher_id", nullable = false)
	private Voucher voucher;

	// Getters & Setters
}
