package com.poly.demo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Ticket_Vouchers")
public class TicketVoucher {

	@EmbeddedId
	private TicketVoucherId id;

	@ManyToOne
	@MapsId("ticketId") // Ánh xạ ticketId từ TicketVoucherId
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@ManyToOne
	@MapsId("voucherId") // Ánh xạ voucherId từ TicketVoucherId
	@JoinColumn(name = "voucher_id", nullable = false)
	private Voucher voucher;

	public TicketVoucher(Ticket ticket, Voucher voucher) {
		this.id = new TicketVoucherId(ticket.getTicketId(), voucher.getVoucherId());
		this.ticket = ticket;
		this.voucher = voucher;
	}
}
