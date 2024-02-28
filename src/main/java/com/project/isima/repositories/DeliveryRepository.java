package com.project.isima.repositories;
import com.project.isima.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Delivery d WHERE d.parcel.id = :idParcel")
    void deleteParcel(@Param("idParcel") Long idParcel);

    @Query("SELECT d FROM Delivery d WHERE d.parcel.id = :idParcel")
    Delivery findByIdParcel(@Param("idParcel") Long idParcel);

    @Transactional
    @Modifying
    @Query("UPDATE Delivery d SET d.deliveryDate = CURRENT_TIMESTAMP WHERE d.parcel.id = :idParcel")
    void updateDateDeliveryByParcelId(@Param("idParcel") Long idParcel);

    @Query(value = "SELECT count(d) FROM Delivery d WHERE d.parcel.id =:idParcel AND d.trip.id=:idTrip")
    Integer findByDetails(@Param("idParcel") Long idParcel, @Param("idTrip") Long idTrip);
}
