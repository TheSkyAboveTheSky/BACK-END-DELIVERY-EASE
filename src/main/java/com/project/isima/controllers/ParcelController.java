package com.project.isima.controllers;

import com.project.isima.entities.Parcel;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.services.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping("/all/{senderId}")
    public List<Parcel> getAllParcels(@PathVariable Long senderId) {
        return parcelService.getAllParcels(senderId);
    }

    @PostMapping("/add/{senderId}")
    public ResponseEntity<String> addNewParcel(@PathVariable Long senderId, @RequestBody Parcel parcel) throws UnauthorizedUserException {
        return ResponseEntity.ok(parcelService.addNewParcel(senderId, parcel));
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
    public ResponseEntity<String> deleteParcel(@PathVariable Long idParcel) {
        return ResponseEntity.ok(parcelService.deleteParcel(idParcel));
    }

}
