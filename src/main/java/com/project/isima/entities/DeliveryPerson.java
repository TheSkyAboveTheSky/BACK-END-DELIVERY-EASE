package com.project.isima.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class DeliveryPerson extends User {
    @Lob
    private byte[] card;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Trip> trips;
}

