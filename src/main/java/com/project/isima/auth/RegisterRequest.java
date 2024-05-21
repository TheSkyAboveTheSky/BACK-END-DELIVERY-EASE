package com.project.isima.auth;

import com.project.isima.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Valid
    @NotNull(message = "First Name should not be null")
    private String firstName;
    @NotNull(message = "Last Name should not be null")
    private String lastName;
    private String picturePath;
    @Email(message = "Invalid email format")
    @NotNull(message = "Email should not be null")
    private String email;
    @NotNull(message = "Password should not be null")
    private String password;
    @NotNull(message = "Role should not be null")
    private Role role;
}
