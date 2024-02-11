package com.project.isima.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JoinColumn(name = "departure_address_id", referencedColumnName = "id")
    private Address departureAddress;

    @ManyToOne
    @JoinColumn(name = "arrival_address_id", referencedColumnName = "id")
    private Address arrivalAddress;

    private Date departureDate;
    private Date arrivalDate;
    private double cost;
    private String description;
    @JsonIgnore
    @ManyToOne // a Trip is associated with a single DeliveryPerson
    @JoinColumn(name = "delivery_person_id", referencedColumnName = "id")
    private User user;
}
