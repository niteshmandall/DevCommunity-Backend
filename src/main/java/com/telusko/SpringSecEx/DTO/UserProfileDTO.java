package com.telusko.SpringSecEx.DTO;

import lombok.Data;

import java.util.List;


@Data
public class UserProfileDTO {
    private String name;
    private String email;
    private String profilePicture;
    private String githubLink;
    private String linkedinLink;
    private List<String> skills;
    private List<String> achievements;
    private List<WorkExperienceDTO> workExperiences;

    // Inner class for work experien

    // Getters and Setters
}

