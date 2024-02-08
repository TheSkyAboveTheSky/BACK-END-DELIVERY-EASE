package com.project.isima.repositories;

import com.project.isima.entities.Parcel;
import com.project.isima.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findAllByUser(User user);
}

