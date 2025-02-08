package com.telusko.SpringSecEx.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_work_experience")
public class UserWorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int experienceId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

    @Column(name = "duration")
    private String duration;

    // Getters and Setters
}
