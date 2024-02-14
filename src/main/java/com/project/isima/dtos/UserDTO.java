package com.project.isima.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    byte[] picture;
    String email;
    double rating = 0.00;
}
