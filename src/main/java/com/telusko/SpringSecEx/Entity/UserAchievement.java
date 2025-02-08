package com.telusko.SpringSecEx.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_achievements")
public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int achievementId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "achievement")
    private String achievement;

    // Getters and Setters
}

