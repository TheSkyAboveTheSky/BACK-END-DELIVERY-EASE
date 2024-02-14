package com.project.isima.repositories;

import com.project.isima.entities.Review;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT AVG(r.starRating) FROM Review r " +
            "JOIN r.delivery d " +
            "WHERE d.id = r.delivery.id AND d.user.id = :deliveryPersonId")
    Object findTotalStarRatingOfDelivery(@Param("deliveryPersonId") Long deliveryPersonId);
}
