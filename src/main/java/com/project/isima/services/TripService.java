package com.project.isima.services;

import com.project.isima.entities.Trip;
import com.project.isima.entities.User;
import com.project.isima.exceptions.TripNotFoundException;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.TripRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public List<Trip> getAllTrips(Long deliveryId) {
        Optional<User> userOptional = userRepository.findById(deliveryId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User user = userOptional.get();
        return tripRepository.findAllByUser(user);
    }

    public String addNewTrip(Long deliveryId, Trip trip) throws UnauthorizedUserException{
        // Récupérer l'identifiant de l'utilisateur actuellement authentifié
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findById(deliveryId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User delivery = userOptional.get();

        // Vérifier si l'utilisateur authentifié correspond à l'utilisateur de la base de données
        if (!delivery.getEmail().equals(authenticatedUserEmail)) {
            throw new UnauthorizedUserException("Unauthorized user for this deliveryId !");
        }

        trip.setUser(delivery);
        tripRepository.save(trip);
        return "Le trajet a été enregistré avec succès.";
    }

    public Trip updateTrip(Trip trip) {
        Trip foundTrip = tripRepository.findById(trip.getId()).orElseThrow(
                ()-> new TripNotFoundException("Trip Not Found !")
        );
        trip.setId(foundTrip.getId());
        trip.setUser(foundTrip.getUser());
        return tripRepository.save(trip);
    }

    public String deleteTrip(Long idTrip) {
        Trip trip = tripRepository.findById(idTrip)
                .orElseThrow(() -> new TripNotFoundException("Trip Not Found !"));
        tripRepository.deleteById(idTrip);
        return "Le trajet a été supprimé avec succès.";
    }

}
