package com.project.isima.services;

import com.project.isima.dtos.ParcelDTO;
import com.project.isima.dtos.UserDTO;
import com.project.isima.entities.Parcel;
import com.project.isima.entities.Trip;
import com.project.isima.exceptions.TripNotFoundException;
import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.TripRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public List<ParcelDTO> getMyParcelsInDelivery(Long idTrip) {

        Trip trip = tripRepository.findById(idTrip)
                .orElseThrow(() -> new TripNotFoundException("Trip Not Found !"));

        List<Parcel> parcels =
                parcelRepository.getAllParcelsInDelivery(trip.getDepartureAddress().getCity(),
                                                         trip.getArrivalAddress().getCity());

        List<ParcelDTO> parcelDTOSList = parcels.stream().map(parcel -> new ParcelDTO(parcel.getId(),
                        parcel.getIdentifier(),
                        parcel.getDescription(),
                        parcel.getStatus(),
                        new UserDTO(trip.getUser().getId(),
                                trip.getUser().getFirstName(),
                                trip.getUser().getLastName(),
                                trip.getUser().getPhoneNumber(),
                                trip.getUser().getEmail(),
                                trip.getUser().getPicturePath())))
                .toList();

        return parcelDTOSList;
    }
}
