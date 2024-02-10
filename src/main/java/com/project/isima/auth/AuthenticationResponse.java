package com.project.isima.auth;

import com.project.isima.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private  String token;
    private Long idUserAuthenticated;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
}