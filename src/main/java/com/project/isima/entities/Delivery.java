package com.project.isima.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date deliveryDate;

    @ManyToOne // a Delivery is associated with a single Parcel
    @JoinColumn(name = "parcel_id", referencedColumnName = "id")
    private Parcel parcel;

    @ManyToOne // a Delivery is associated with a single Parcel
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @ManyToOne // a Delivery is associated with a single User (Delivery Person)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany
    private List<Review> reviewList;
}
