package com.telusko.SpringSecEx.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedProjectDTO {
    private Integer requestId;
    private Integer userId;
    private Long projectId;
    private String requestStatus;
    private Date requestedAt;

    // Constructors, Getters, and Setters
}


