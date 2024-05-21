package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.entities.Review;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add/{idDelivery}")
    public ResponseEntity<ResponseMessage> AddNewReviewToDelivery(@PathVariable Long idDelivery, @RequestBody Review review) throws UnauthorizedUserException {
        return ResponseEntity.ok(reviewService.AddNewReviewToDelivery(idDelivery, review));
    }
}
