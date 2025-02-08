package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.Entity.AllowedSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowedSkillRepository extends JpaRepository<AllowedSkill, Integer> {
    AllowedSkill findBySkillName(String skillName);
}
