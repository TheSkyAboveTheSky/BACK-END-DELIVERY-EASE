package com.project.isima.repositories;
import com.project.isima.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Delivery d WHERE d.parcel.id = :idParcel")
    void deleteParcel(@Param("idParcel") Long idParcel);
}
