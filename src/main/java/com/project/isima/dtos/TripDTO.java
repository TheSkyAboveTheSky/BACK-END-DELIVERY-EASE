package com.project.isima.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {
    Long id;
    AddressDTO departureAddress;
    AddressDTO arrivalAddress;
    Date departureDate;
    Date arrivalDate;
    double cost;
    String description;
    UserDTO user;
}
