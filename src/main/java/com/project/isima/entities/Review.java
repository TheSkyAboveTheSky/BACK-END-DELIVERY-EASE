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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer starRating;
    private String comment;
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
