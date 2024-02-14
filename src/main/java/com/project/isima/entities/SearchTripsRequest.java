package com.project.isima.entities;

import lombok.Data;

@Data
public class SearchTripsRequest {
    private Address departureAddress;
    private Address arrivalAddress;
}
