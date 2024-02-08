package com.project.isima.controllers;

import com.project.isima.entities.Trip;
import com.project.isima.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping("/{deliveryId}")
    public List<Trip> getAllTrips(@PathVariable Long deliveryId) {
        return tripService.getAllTrips(deliveryId);
    }

    @PostMapping("/add/{deliveryId}")
    public Trip addNewTrip(@PathVariable Long deliveryId, @RequestBody Trip trip) {
        return tripService.addNewTrip(deliveryId, trip);
    }

    @DeleteMapping("{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}
