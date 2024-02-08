package com.project.isima.controllers;

import com.project.isima.entities.Parcel;
import com.project.isima.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parcels")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;

    @GetMapping("/{senderId}")
    public List<Parcel> getAllParcels(@PathVariable Long senderId) {
        return parcelService.getAllParcels(senderId);
    }

    @PostMapping("/add/{senderId}")
    public Parcel addNewParcel(@PathVariable Long senderId, @RequestBody Parcel parcel) {
        return parcelService.addNewParcel(senderId, parcel);
    }

    @PutMapping("{id}")
    public ResponseEntity<Parcel> updateParcel(@PathVariable Long id, @RequestBody Parcel parcel) {
        parcel.setId(id);
        Parcel updatedParcel = parcelService.updateParcel(parcel);
        if (updatedParcel != null) {
            return new ResponseEntity<>(updatedParcel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public void deleteParcel(@PathVariable Long id) {
        parcelService.deleteParcel(id);
    }

}
