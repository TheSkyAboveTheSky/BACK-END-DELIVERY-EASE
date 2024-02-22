package com.project.isima.repositories;

import com.project.isima.entities.Parcel;
import com.project.isima.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    @Override
    Optional<Parcel> findById(Long aLong);

    List<Parcel> findAllByUser(User user);
    @Query("SELECT p FROM Parcel p " +
            "JOIN p.shippingAddress sa " +
            "JOIN p.destinationAddress da " +
            "WHERE sa.city = :departureCity " +
            "AND da.city = :arrivalCity " +
            "AND p.id IN (SELECT dp.parcel.id FROM Delivery dp)")
    List<Parcel> getAllTripsInDelivery(@Param("departureCity") String departureCity, @Param("arrivalCity") String arrivalCity);

    @Query("SELECT p FROM Parcel p " +
            "JOIN Delivery d ON p.id = d.parcel.id " +
            "JOIN User u ON d.user = u " +
            "WHERE p.user.id = :idSender " +
            "AND d.user.id = :idDelivery")
    Optional<List<Parcel>> findMyParcelsWithDelivery(
            @Param("idSender") Long idSender,
            @Param("idDelivery") Long idDelivery);
}

