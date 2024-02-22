package com.project.isima.mappers;

import com.project.isima.dtos.UserDTO;
import com.project.isima.entities.User;
import com.project.isima.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.project.isima.entities.ImageConstants.BASE_URL;

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
                BASE_URL + user.getPicturePath(),
                user.getEmail(),
                reviewRepository.findTotalStarRatingOfDelivery(user.getId()) == null ? 0.00:(double)reviewRepository.findTotalStarRatingOfDelivery(user.getId()));
    }
}
