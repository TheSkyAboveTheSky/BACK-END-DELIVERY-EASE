package com.project.isima.entities;

import com.project.isima.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionResponse {
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status action;
}
