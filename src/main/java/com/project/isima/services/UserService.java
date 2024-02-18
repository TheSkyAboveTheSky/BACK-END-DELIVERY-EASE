package com.project.isima.services;

import com.project.isima.dtos.UserDTO;
import com.project.isima.dtos.UserDTOAuthenticate;
import com.project.isima.entities.User;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTOAuthenticate getUserInfosById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        return new UserDTOAuthenticate(user.getFirstName(), user.getLastName());
    }

    public  User getUserByEmail(String email) {
        return  userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
    }

    public UserDTO updateUserInfo(Map<String, Object> fields) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> foundUser = userRepository.findUserByEmail(authenticatedUserEmail);

        if(foundUser.isPresent()) {
            User user = foundUser.get();
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            });
            userRepository.save(user);
            return new UserDTO(user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());
        }
        return null;
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        userRepository.deleteById(id);
    }
}
