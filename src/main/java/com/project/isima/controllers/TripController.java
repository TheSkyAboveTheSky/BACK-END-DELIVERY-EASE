package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.TripDTO;
import com.project.isima.dtos.TripDTOForDelivery;
import com.project.isima.entities.SearchTripsRequest;
import com.project.isima.entities.Trip;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@CrossOrigin
public class TripController {
    private final TripService tripService;

    @GetMapping("/all")
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<TripDTO> trips;
        try {
            trips = tripService.getAllTrips();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trips);
    }

    @PostMapping("/searchTrips")
    public ResponseEntity<List<TripDTO>> searchTrips(@RequestBody SearchTripsRequest searchTripsRequest) {
        return ResponseEntity.ok(tripService.searchTrips(searchTripsRequest));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addNewTrip(@RequestBody Trip trip) throws UnauthorizedUserException {
        return ResponseEntity.ok(tripService.addNewTrip(trip));
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
    public ResponseEntity<ResponseMessage> deleteTrip(@PathVariable Long idTrip) {
        return ResponseEntity.ok(tripService.deleteTrip(idTrip));
    }
}
