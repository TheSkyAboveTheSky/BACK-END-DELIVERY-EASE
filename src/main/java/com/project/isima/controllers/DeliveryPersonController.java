package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.entities.ActionResponse;
import com.project.isima.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveryPerson")
@CrossOrigin
@RequiredArgsConstructor
public class DeliveryPersonController {

    private final DeliveryService deliveryService;
    @PatchMapping("/action")
    public ResponseEntity<ResponseMessage> actionToDemandDelivery(@RequestBody ActionResponse actionResponse) {
        return ResponseEntity.ok(deliveryService.actionToDemandDelivery(actionResponse));
    }
}
