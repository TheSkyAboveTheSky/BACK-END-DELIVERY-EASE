package com.project.isima.services;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.ParcelDTO;
import com.project.isima.dtos.UserDTO;
import com.project.isima.entities.*;
import com.project.isima.enums.Role;
import com.project.isima.enums.Status;
import com.project.isima.exceptions.ParcelNotFoundException;
import com.project.isima.exceptions.TripNotFoundException;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.DeliveryRepository;
import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.TripRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final ParcelRepository parcelRepository;

    private final TripRepository tripRepository;

    public ResponseMessage addNewDemandDelivery(Delivery delivery) throws UnauthorizedUserException {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findUserByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UserNotFoundException("Sender Not Found."));

        User deliveryPerson = userRepository.findUserByEmail(delivery.getUser().getEmail())
                .orElseThrow(() -> new UserNotFoundException("Delivery Person Not Found."));

        if(!deliveryPerson.getRole().equals(Role.DELIVERY_PERSON)) {
            throw new UnauthorizedUserException("This is not a Delivery Person.");
        }
        
        Parcel parcel = parcelRepository.findById(delivery.getParcel().getId())
                .orElseThrow(() -> new ParcelNotFoundException("Parcel Not Found."));
        // Assigner l'utilisateur expéditeur au colis s'il n'est pas déjà assigné
        if (parcel.getUser() == null) {
            parcel.setUser(sender);
            parcelRepository.save(parcel);
        }

        parcel.setStatus(Status.UNCONFIRMED);

        // Assigner l'utilisateur livreur à la livraison
        delivery.setUser(deliveryPerson);
        // Assigner le colis à la livraison
        delivery.setParcel(parcel);

        deliveryRepository.save(delivery);
        return new ResponseMessage("La demande de livraison a été envoyée avec succès.");
    }

    public List<ParcelDTO> getAllDemands(Long idTrip) {
        Trip trip = tripRepository.findById(idTrip)
                .orElseThrow(() -> new TripNotFoundException("Trip Not Found."));

        List<Parcel> parcels = parcelRepository.getAllTripsInDelivery(trip.getDepartureAddress().getCity(), trip.getArrivalAddress().getCity());

        List<ParcelDTO> parcelDTOSList = parcels.stream().map(parcel -> new ParcelDTO(parcel.getId(),
                parcel.getDescription(),
                parcel.getStatus(),
                new UserDTO(parcel.getUser().getFirstName(), parcel.getUser().getLastName())))
                .toList();
        return parcelDTOSList;
    }

    public ResponseMessage actionToDemandDelivery(ActionResponse actionResponse) {

        Parcel parcel = parcelRepository.findById(actionResponse.getId())
                .orElseThrow(() -> new ParcelNotFoundException("Parcel Not Found."));

        parcel.setStatus(actionResponse.getAction());

        if(actionResponse.getAction().equals(Status.REFUSED)) {
            deliveryRepository.deleteParcel(actionResponse.getId());
        }

        parcelRepository.save(parcel);
        return new ResponseMessage("Le status de colis est modifie.");
    }
}