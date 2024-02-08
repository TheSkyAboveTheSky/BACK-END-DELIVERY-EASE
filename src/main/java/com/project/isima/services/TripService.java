package com.project.isima.services;

import com.project.isima.entities.Parcel;
import com.project.isima.entities.Trip;
import com.project.isima.entities.User;
import com.project.isima.exceptions.TripNotFoundException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.TripRepository;
import com.project.isima.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Trip> getAllTrips(Long deliveryId) {
        Optional<User> userOptional = userRepository.findById(deliveryId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User user = userOptional.get();
        return tripRepository.findAllByUser(user);
    }

    public Trip addNewTrip(Long deliveryId, Trip trip) {
        Optional<User> userOptional = userRepository.findById(deliveryId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User delivery = userOptional.get();
        trip.setUser(delivery);
        return tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip Not Found !"));
        tripRepository.deleteById(id);
    }
}
