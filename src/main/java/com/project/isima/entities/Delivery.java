package com.project.isima.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

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

    @OneToOne // a Delivery is associated with a single Parcel
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;

    @OneToOne // a Delivery is associated with a single Parcel
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne //
    @JoinColumn(name = "user_id")
    private User user;
}
