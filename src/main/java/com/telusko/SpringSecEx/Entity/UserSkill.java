package com.telusko.SpringSecEx.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_skills")
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSkillId;

    private int userId;

    private int skillId;

    private String skill;
}
