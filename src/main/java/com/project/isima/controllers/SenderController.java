package com.project.isima.controllers;


import com.project.isima.dtos.ParcelDTO;
import com.project.isima.entities.Parcel;
import com.project.isima.services.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/senders")
@CrossOrigin
@RequiredArgsConstructor
public class SenderController {

    private final SenderService senderService;
    @GetMapping("/myParcels/{idTrip}")
    public ResponseEntity<List<ParcelDTO>> getMyParcels(@PathVariable Long idTrip) {
        return ResponseEntity.ok(senderService.getMyParcelsInDelivery(idTrip));
    }
}
