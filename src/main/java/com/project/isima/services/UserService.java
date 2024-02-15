package com.project.isima.services;

import com.project.isima.dtos.UserDTO;
import com.project.isima.entities.User;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
    }

    public  User getUserByEmail(String email) {
        return  userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
    }

    public UserDTO updateUserInfo(User user) {

        System.out.println("Ici...");
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> foundUser = userRepository.findUserByEmail(authenticatedUserEmail);
        User u = foundUser.get();
        user.setId(u.getId());
        user.setPassword(u.getPassword());
        user.setRole(u.getRole());
        User userModified = userRepository.save(user);

        return new UserDTO(userModified.getFirstName(), userModified.getLastName(), userModified.getPhoneNumber(), userModified.getEmail());
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        userRepository.deleteById(id);
    }
}
