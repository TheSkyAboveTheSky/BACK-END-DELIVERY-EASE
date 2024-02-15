package com.project.isima.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor

public class TripDTO {
    Long id;
    AddressDTO departureAddress;
    AddressDTO arrivalAddress;
    Date departureDate;
    Date arrivalDate;
    double cost;
    String description;
    UserDTO user;
    public TripDTO(AddressDTO departureAddress, AddressDTO arrivalAddress, Date departureDate) {
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.departureDate = departureDate;
    }
}
