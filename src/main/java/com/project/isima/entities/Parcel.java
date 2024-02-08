package com.project.isima.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.isima.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;
    private String destinationAddress;
    private String shippingAddress;
    private String description;
    private Status status;
    @JsonIgnore
    @ManyToOne // a Parcel is associated with a single sender
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User user;

    public Parcel() {

    }

    public Parcel(String identifier,
                  String destinationAddress,
                  String shippingAddress,
                  String description) {
        this.identifier = identifier;
        this.destinationAddress = destinationAddress;
        this.shippingAddress = shippingAddress;
        this.description = description;
        this.status = Status.UNCONFIRMED;
    }
}
