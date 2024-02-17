package com.project.isima.dtos;

import com.project.isima.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDTO {
    private Long id;
    private String description;
    private Status status;
    private UserDTO userDTO;
}
