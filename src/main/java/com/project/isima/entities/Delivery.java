package com.project.isima.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Delivery {
    @Id
    private Long id;
    private Date deliveryDate;

    @ManyToOne // a Delivery is associated with a single Parcel
    @JoinColumn(name = "parcel_id", referencedColumnName = "id")
    private Parcel parcel;

    @ManyToOne // a Delivery is associated with a single User (Delivery Person)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany
    private List<Review> reviewList;
}
