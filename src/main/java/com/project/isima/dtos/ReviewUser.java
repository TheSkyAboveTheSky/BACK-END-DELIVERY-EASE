package com.project.isima.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUser {
    private Long id;
    private Integer starRating;
    private String comment;
    private Date reviewDate;
    private UserDTO user;
}
