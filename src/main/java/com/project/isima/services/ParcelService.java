package com.project.isima.services;

import com.project.isima.auth.AthenticationResponseMessage;
import com.project.isima.entities.Parcel;
import com.project.isima.entities.User;
import com.project.isima.enums.Role;
import com.project.isima.enums.Status;
import com.project.isima.exceptions.ParcelNotFoundException;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.AddressRepository;
import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public List<Parcel> getAllParcels(Long senderId) {
        Optional<User> userOptional = userRepository.findById(senderId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User user = userOptional.get();
        if(!user.getRole().equals(Role.SENDER)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not a sender !");
        }
        return parcelRepository.findAllByUser(user);
    }

    public AthenticationResponseMessage addNewParcel(Long senderId, Parcel parcel) throws UnauthorizedUserException {
        // Récupérer l'identifiant de l'utilisateur actuellement authentifié
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findById(senderId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User sender = user.get();

        // Vérifier si l'utilisateur authentifié correspond à l'utilisateur de la base de données
        if (!sender.getEmail().equals(authenticatedUserEmail)) {
            throw new UnauthorizedUserException("Unauthorized user for this senderId !");
        }

        parcel.setUser(sender);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
        parcel.setIdentifier(uuid);

        parcel.setStatus(Status.UNCONFIRMED);

        addressRepository.save(parcel.getDestinationAddress());
        addressRepository.save(parcel.getShippingAddress());

        parcelRepository.save(parcel);

        return new AthenticationResponseMessage("Le colis a été enregistré avec succès.");
    }

    public Parcel updateParcel(Parcel parcel) {
        Parcel found = parcelRepository.findById(parcel.getId()).orElseThrow(
                ()-> new ParcelNotFoundException("Parcel Not Found !")
        );
        parcel.setIdentifier(found.getIdentifier());
        parcel.setStatus(found.getStatus());
        parcel.setUser(found.getUser());
        return parcelRepository.save(parcel);
    }

    public AthenticationResponseMessage deleteParcel(Long idParcel) throws ParcelNotFoundException {
        Parcel parcel = parcelRepository.findById(idParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Parcel Not Found !"));
        parcelRepository.deleteById(idParcel);
        return new AthenticationResponseMessage("Le colis a été supprimé avec succès.");
    }
}
