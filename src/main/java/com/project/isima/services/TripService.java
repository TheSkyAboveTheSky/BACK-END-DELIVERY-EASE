package com.project.isima.services;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.AddressDTO;
import com.project.isima.dtos.TripDTO;
import com.project.isima.entities.SearchTripsRequest;
import com.project.isima.entities.Trip;
import com.project.isima.entities.User;
import com.project.isima.enums.Role;
import com.project.isima.exceptions.TripNotFoundException;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.mappers.TripDTOMapper;
import com.project.isima.repositories.AddressRepository;
import com.project.isima.repositories.TripRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TripDTOMapper tripDTOMapper;

    public List<Trip> getAllTrips() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findUserByEmail(authenticatedUserEmail);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User user = userOptional.get();
        if(!user.getRole().equals(Role.DELIVERY_PERSON)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not a delivery person!");
        }
        return tripRepository.findAllByUser(user);
    }

    public ResponseMessage addNewTrip(Trip trip) throws UnauthorizedUserException{
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findUserByEmail(authenticatedUserEmail);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User delivery = userOptional.get();

        trip.setUser(delivery);

        addressRepository.save(trip.getDepartureAddress());
        addressRepository.save(trip.getArrivalAddress());

        tripRepository.save(trip);
        return new ResponseMessage("Le trajet a été enregistré avec succès.");
    }

    public Trip updateTrip(Trip trip) {
        Trip foundTrip = tripRepository.findById(trip.getId()).orElseThrow(
                ()-> new TripNotFoundException("Trip Not Found !")
        );
        addressRepository.save(trip.getDepartureAddress());
        addressRepository.save(trip.getArrivalAddress());

        trip.setUser(foundTrip.getUser());
        return tripRepository.save(trip);
    }

    public ResponseMessage deleteTrip(Long idTrip) {
        tripRepository.findById(idTrip)
                .orElseThrow(() -> new TripNotFoundException("Trip Not Found !"));
        tripRepository.deleteById(idTrip);
        return new ResponseMessage("Le trajet a été supprimé avec succès.");
    }

    public List<TripDTO> searchTrips(SearchTripsRequest searchTripsRequest) {
        AddressDTO departureAddress = new AddressDTO(
                null,
                searchTripsRequest.getDepartureAddress().getCity()
        );
        AddressDTO arrivalAddress = new AddressDTO(
                null,
                searchTripsRequest.getArrivalAddress().getCity()
        );

        // Récupérer tous les trajets
        List<Trip> allTrips = tripRepository.findAll();

        // Filtrer les trajets pour ne conserver que ceux correspondant aux villes spécifiées
        List<TripDTO> filteredTrips = allTrips.stream()
                .filter(trip ->
                        trip.getDepartureAddress().getCity().equalsIgnoreCase(departureAddress.getCity())
                                && trip.getArrivalAddress().getCity().equalsIgnoreCase(arrivalAddress.getCity())
                )
                .sorted(Comparator.comparingDouble(Trip::getCost))
                .map(tripDTOMapper)
                .collect(Collectors.toList());

        return filteredTrips;
    }
}
