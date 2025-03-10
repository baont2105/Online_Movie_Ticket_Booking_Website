package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Integer voucherId;

    private String code;
    
    @Column(name = "discount_amount")
    private Integer discountAmount;
}