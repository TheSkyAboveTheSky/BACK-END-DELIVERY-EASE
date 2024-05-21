package com.project.isima.services;

import com.project.isima.auth.RegisterRequest;
import com.project.isima.config.JwtService;
import com.project.isima.entities.User;
import com.project.isima.enums.Role;
import com.project.isima.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;
    private AuthenticationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void canRegister() {
        // given
            RegisterRequest userInfos = new RegisterRequest(
                    "Mouad",
                    "Kabouri",
                    null,
                    "sender@gmail.com",
                    "1234",
                    Role.SENDER
            );

        // when
            try {
                underTest.register(userInfos);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        // then
            ArgumentCaptor<User> userArgumentCaptor =
                    ArgumentCaptor.forClass(User.class);

            verify(userRepository).save(userArgumentCaptor.capture());

            User capturedUser = userArgumentCaptor.getValue();

            assertThat(capturedUser.getFirstName()).isEqualTo(userInfos.getFirstName());
            assertThat(capturedUser.getLastName()).isEqualTo(userInfos.getLastName());
            assertThat(capturedUser.getEmail()).isEqualTo(userInfos.getEmail());
    }

    @Test
    @Disabled
    void authenticate() {

    }
}