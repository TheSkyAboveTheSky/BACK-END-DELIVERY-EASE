package com.project.isima.mappers;

import com.project.isima.dtos.TripDTO;
import com.project.isima.entities.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TripDTOMapper implements Function<Trip, TripDTO> {

    private final AddressDTOMapper addressDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Override
    public TripDTO apply(Trip trip) {
        return new TripDTO(
                trip.getId(),
                addressDTOMapper.apply(trip.getDepartureAddress()), // Appel à la méthode apply() ajouté
                addressDTOMapper.apply(trip.getArrivalAddress()),
                trip.getDepartureDate(),
                trip.getArrivalDate(),
                trip.getCost(),
                trip.getDescription(),
                userDTOMapper.apply(trip.getUser()));
    }
}
