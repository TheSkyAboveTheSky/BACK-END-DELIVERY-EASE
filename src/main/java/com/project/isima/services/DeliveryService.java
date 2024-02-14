package com.project.isima.services;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.entities.Delivery;
import com.project.isima.entities.Parcel;
import com.project.isima.entities.User;
import com.project.isima.enums.Role;
import com.project.isima.exceptions.ParcelNotFoundException;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.DeliveryRepository;
import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final ParcelRepository parcelRepository;
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

        // Assigner l'utilisateur livreur à la livraison
        delivery.setUser(deliveryPerson);
        // Assigner le colis à la livraison
        delivery.setParcel(parcel);

        deliveryRepository.save(delivery);
        return new ResponseMessage("La demande de livraison a été envoyée avec succès.");
    }
}
