package com.project.isima.entities;

import com.project.isima.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First Name should not be null")
    private String firstName;
    @NotNull(message = "Last Name should not be null")
    private String lastName;
    private String phoneNumber;
    @Lob
    private byte[] picture;
    @Column(unique = true)
    @Email(message = "Invalid email format")
    @NotNull(message = "Email should not be null")
    private String email;
    @NotNull(message = "Password should not be null")
    @NotEmpty
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    //@OneToMany(cascade = CascadeType.PERSIST)
    //private List<Parcel> parcels;
}
