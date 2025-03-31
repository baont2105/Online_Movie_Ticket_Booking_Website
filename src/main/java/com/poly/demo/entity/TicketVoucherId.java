package com.poly.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
