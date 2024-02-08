package com.project.isima.repositories;

import com.project.isima.entities.Parcel;
import com.project.isima.entities.Trip;
import com.project.isima.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByUser(User user);
}
