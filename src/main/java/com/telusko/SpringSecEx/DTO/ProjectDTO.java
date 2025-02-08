package com.telusko.SpringSecEx.DTO;



import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long projectId;
    private String title;
    private String description;
    private List<String> techStack;
    private String difficulty;
    private String status;
    private String leadName;  // Only fetch lead's name, not all user details
}
