package com.telusko.SpringSecEx.DTO;

import com.telusko.SpringSecEx.Entity.RequestedProject.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectWithRequestStatusDTO {
    private Long projectId;
    private String title;
    private String description;
    private List<String> techStack;
    private String difficulty;
    private String status;
    private String leadName;
    private RequestStatus requestStatus;  // Include the request status
}
