package com.project.isima.repositories;

import com.project.isima.entities.Review;
import com.project.isima.entities.User;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT AVG(r.starRating) FROM Review r " +
            "JOIN r.delivery d " +
            "WHERE d.id = r.delivery.id AND d.user.id = :deliveryPersonId")
    Object findTotalStarRatingOfDelivery(@Param("deliveryPersonId") Long deliveryPersonId);

    @Query("SELECT r FROM Review r " +
            "WHERE r.delivery IN (SELECT d FROM Delivery d WHERE d.user = :deliveryPerson)")
    List<Review> findAllDeliveryPerson(@Param("deliveryPerson") User deliveryPerson);
}
