package com.project.isima.services;

import com.project.isima.entities.Parcel;
import com.project.isima.entities.User;
import com.project.isima.exceptions.ParcelNotFoundException;
import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;

    public List<Parcel> getMyParcelsWithDelivery(Long idDelivery) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User sender = userRepository.findUserByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new ParcelNotFoundException("Sender Not Found !"));

        Optional<List<Parcel>> parcels = parcelRepository.findMyParcelsWithDelivery(sender.getId(), idDelivery);

        return parcels.get();
    }
}
