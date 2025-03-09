package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Ticket_Vouchers")
public class TicketVoucher {
    @EmbeddedId
    private TicketVoucherId id;

    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @MapsId("voucherId")
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
}