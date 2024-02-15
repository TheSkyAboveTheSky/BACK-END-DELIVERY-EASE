package com.project.isima.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    byte[] picture;
    String email;
    double rating = 0.00;

    public UserDTO(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
