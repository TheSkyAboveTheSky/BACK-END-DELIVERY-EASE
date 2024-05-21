package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.ParcelDTO;
import com.project.isima.entities.Delivery;
import com.project.isima.entities.Parcel;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
@CrossOrigin
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{idParcel}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable Long idParcel) {
        Delivery delivery = deliveryService.getDeliveryByIdParcel(idParcel);
        if (delivery != null) {
            return ResponseEntity.ok(delivery);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/demand")
    public ResponseEntity<ResponseMessage> addNewDemandDelivery(@RequestBody Delivery delivery) throws UnauthorizedUserException {
        return ResponseEntity.ok(deliveryService.addNewDemandDelivery(delivery));
    }

    @GetMapping("/demands/{idTrip}")
    public ResponseEntity<List<ParcelDTO>> getAllDemands(@PathVariable Long idTrip) {
        return ResponseEntity.ok(deliveryService.getAllDemands(idTrip));
    }

    @GetMapping("/all/{idUser}") // to get all deliveries between sender and delivery man
    public ResponseEntity<List<Delivery>> getAllDeliveries(@PathVariable Long idUser) {
        return ResponseEntity.ok(deliveryService.getAllDeliveries(idUser));
    }
}
