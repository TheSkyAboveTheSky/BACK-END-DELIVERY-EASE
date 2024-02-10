package com.project.isima.enums.services;

import com.project.isima.entities.User;
import com.project.isima.exceptions.EmailAlreadyExistsException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User addNewUser(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if(userByEmail.isPresent()) {
            throw new EmailAlreadyExistsException("Email taken !");
        }

        String pw = user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
    }
}
