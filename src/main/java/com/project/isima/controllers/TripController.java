package com.project.isima.controllers;

import com.project.isima.entities.Trip;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    @GetMapping("/all/{deliveryId}")
    public List<Trip> getAllTrips(@PathVariable Long deliveryId) {
        return tripService.getAllTrips(deliveryId);
    }

    @PostMapping("/add/{deliveryId}")
    public ResponseEntity<String> addNewTrip(@PathVariable Long deliveryId, @RequestBody Trip trip) throws UnauthorizedUserException {
        return ResponseEntity.ok(tripService.addNewTrip(deliveryId, trip));
    }

    @PutMapping("/update/{idTrip}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long idTrip, @RequestBody Trip trip) {
        trip.setId(idTrip);
        Trip updatedTrip = tripService.updateTrip(trip);
        if (updatedTrip != null) {
            return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{idTrip}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long idTrip) {
        return ResponseEntity.ok(tripService.deleteTrip(idTrip));
    }
}
