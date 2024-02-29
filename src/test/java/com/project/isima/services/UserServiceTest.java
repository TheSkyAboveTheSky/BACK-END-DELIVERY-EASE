package com.project.isima.services;

import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.ReviewRepository;
import com.project.isima.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private ParcelRepository parcelRepository;
    @Mock private ReviewRepository reviewRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, parcelRepository, reviewRepository);
    }

    @Test
    void canGetAllUsers() {
        // when
            underTest.getAllUsers();
        // then
            verify(userRepository).findAll();
    }

    @Test
    @Disabled
    void getUserInfosById() {

    }

    @Test
    @Disabled
    void getMyInfos() {

    }

    @Test
    @Disabled
    void getParcelsOfUserById() {

    }

    @Test
    @Disabled
    void getReviewOfUserById() {

    }

    @Test
    @Disabled
    void updateUserInfo() {

    }

    @Test
    @Disabled
    void updateUserPictureProfile() {

    }

    @Test
    @Disabled
    void updateUserAccountStatus() {

    }

    @Test
    @Disabled
    void deleteUser() {

    }
}