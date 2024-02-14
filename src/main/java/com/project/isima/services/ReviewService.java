package com.project.isima.services;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.entities.Delivery;
import com.project.isima.entities.Review;
import com.project.isima.entities.User;
import com.project.isima.enums.Role;
import com.project.isima.exceptions.DeliveryNotFoundException;
import com.project.isima.exceptions.UnauthorizedUserException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.DeliveryRepository;
import com.project.isima.repositories.ReviewRepository;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final ReviewRepository reviewRepository;
    public ResponseMessage AddNewReviewToDelivery(Long idDelivery, Review review) throws UnauthorizedUserException {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findUserByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UserNotFoundException("Sender Not Found."));

        if(!sender.getRole().equals(Role.SENDER)) {
            throw new UnauthorizedUserException("This is not a Sender.");
        }

        Delivery delivery = deliveryRepository.findById(idDelivery)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found."));

        review.setReviewDate(new Date());
        review.setDelivery(delivery);
        reviewRepository.save(review);
        return new ResponseMessage("Votre avis a été ajouté avec succès. Merci !");
    }
}
