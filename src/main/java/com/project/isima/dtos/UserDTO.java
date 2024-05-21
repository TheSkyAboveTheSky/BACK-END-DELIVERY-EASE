package com.project.isima.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String picture;
    String email;
    double rating;

    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(String firstName, String lastName, String phoneNumber, String email) {
        this(firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserDTO(String firstName, String lastName, String phoneNumber, String email, String picture) {
        this(firstName, lastName, phoneNumber, email);
        this.picture = picture;
    }

    public UserDTO(Long id, String firstName, String lastName, String phoneNumber, String email, String picture) {
        this(firstName, lastName, phoneNumber, email, picture);
        this.id = id;
    }
}
