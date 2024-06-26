package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.entities.Parcel;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.services.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parcels")
@RequiredArgsConstructor
@CrossOrigin
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping("/all")
    public ResponseEntity<List<Parcel>> getAllParcels() {
        List<Parcel> parcels;
        try {
            parcels = parcelService.getAllParcels();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retourne une réponse 404
        }
        return ResponseEntity.ok(parcels);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addNewParcel(@RequestBody Parcel parcel) throws UnauthorizedUserException {
        return ResponseEntity.ok(parcelService.addNewParcel(parcel));
    }

    @PutMapping("/update/{idParcel}")
    public ResponseEntity<Parcel> updateParcel(@PathVariable Long idParcel, @RequestBody Parcel parcel) {
        parcel.setId(idParcel);
        Parcel updatedParcel = parcelService.updateParcel(parcel);
        if (updatedParcel != null) {
            return new ResponseEntity<>(updatedParcel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{idParcel}")
    public ResponseEntity<ResponseMessage> deleteParcel(@PathVariable Long idParcel) {
        return ResponseEntity.ok(parcelService.deleteParcel(idParcel));
    }

}
