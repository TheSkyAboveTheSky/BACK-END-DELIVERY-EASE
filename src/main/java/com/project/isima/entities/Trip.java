package com.project.isima.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "departure_address_id")
    private Address departureAddress;

    @OneToOne
    @JoinColumn(name = "arrival_address_id")
    private Address arrivalAddress;

    private Date departureDate;
    private Date arrivalDate;
    private double cost;
    private String description;

    @ManyToOne // A DeliveryPerson can have many Trips
    @JoinColumn(name = "delivery_person_id")
    private User user;
}
