package com.project.isima.controllers;


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
    @GetMapping("/myParcels/{idDelivery}")
    public ResponseEntity<List<Parcel>> getMyParcels(@PathVariable Long idDelivery) {
        return ResponseEntity.ok(senderService.getMyParcelsWithDelivery(idDelivery));
    }
}
