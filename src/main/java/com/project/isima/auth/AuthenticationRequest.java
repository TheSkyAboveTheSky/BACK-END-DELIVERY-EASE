package com.project.isima.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Valid
    @Email(message = "Invalid email format")
    @NotNull(message = "Email should not be null")
    private String email;
    @NotNull(message = "Password should not be null")
    private String password;
}
