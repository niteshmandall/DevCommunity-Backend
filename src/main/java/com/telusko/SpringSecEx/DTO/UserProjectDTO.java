package com.telusko.SpringSecEx.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProjectDTO {
//    private Integer userId;       // Add userId
    private Long projectId;       // Add projectId
    private String role;
    private LocalDateTime assignedAt;
}
