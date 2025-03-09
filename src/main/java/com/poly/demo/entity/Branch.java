package com.poly.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchId;

    private String name;
    private String address;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    private String email;
    private String city;
    private Boolean visible;
}