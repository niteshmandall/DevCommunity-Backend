package com.telusko.SpringSecEx.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "allowed_skills")
public class AllowedSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int skillId;

    @Column(name = "skill_name", unique = true, nullable = false)
    private String skillName;

    // Getters and setters
}

