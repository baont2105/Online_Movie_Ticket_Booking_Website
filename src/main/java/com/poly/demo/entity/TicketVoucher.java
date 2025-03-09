package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ticket_Vouchers")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    public TicketVoucher(Ticket ticket, Voucher voucher) {
        this.ticket = ticket;
        this.voucher = voucher;
        this.id = new TicketVoucherId(ticket.getTicketId(), voucher.getVoucherId());
    }
}
