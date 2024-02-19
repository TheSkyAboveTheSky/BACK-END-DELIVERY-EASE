package com.project.isima.dtos;

import com.project.isima.enums.AccountStatus;
import com.project.isima.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForAdmin {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String picture;
    Role role;
    AccountStatus accountStatus;
}
