package com.poly.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketVoucherId implements Serializable {
	@Column(name = "ticket_id")
    private Integer ticketId;
	
	@Column(name = "voucher_id")
    private Integer voucherId;
}
