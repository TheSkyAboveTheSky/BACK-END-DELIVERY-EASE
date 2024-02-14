package com.project.isima.mappers;

import com.project.isima.dtos.UserDTO;
import com.project.isima.entities.User;
import com.project.isima.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserDTOMapper implements Function<User, UserDTO> {
    private final ReviewRepository reviewRepository;
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getPicture(),
                user.getEmail(),
                reviewRepository.findTotalStarRatingOfDelivery(user.getId()) == null ? 0.00:(double)reviewRepository.findTotalStarRatingOfDelivery(user.getId()));
    }
}
