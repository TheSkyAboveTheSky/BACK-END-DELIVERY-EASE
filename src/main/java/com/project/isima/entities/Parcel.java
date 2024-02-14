package com.project.isima.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.isima.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;
    @ManyToOne
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;
    @ManyToOne
    @JoinColumn(name = "destination_address_id", referencedColumnName = "id")
    private Address destinationAddress;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @JsonIgnore
    @ManyToOne // a Parcel is associated with a single sender
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User user;
}
