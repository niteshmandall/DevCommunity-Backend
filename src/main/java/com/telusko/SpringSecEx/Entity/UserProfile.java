package com.telusko.SpringSecEx.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "github_link")
    private String githubLink;

    @Column(name = "linkedin_link")
    private String linkedinLink;

    // Getters and Setters
}

