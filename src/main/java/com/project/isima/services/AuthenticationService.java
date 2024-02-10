package com.project.isima.services;

import com.project.isima.auth.AuthenticationRequest;
import com.project.isima.auth.AuthenticationResponse;
import com.project.isima.auth.RegisterRequest;
import com.project.isima.config.JwtService;
import com.project.isima.entities.User;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        Optional<User> u = userRepository.findUserByEmail(request.getEmail());
        if(u.isPresent()) {
            return "Un compte avec cet e-mail existe déjà.";
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return "Le compte a été créé avec succès.";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User avec ce mail " + request.getEmail() + " n'existe pas"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .idUserAuthenticated(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
