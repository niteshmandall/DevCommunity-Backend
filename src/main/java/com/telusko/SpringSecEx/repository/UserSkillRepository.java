package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.Entity.UserSkill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {
    List<UserSkill> findByUserId(int userId);

    @Query("SELECT a.skillName FROM UserSkill us JOIN AllowedSkill a ON us.skillId = a.skillId WHERE us.userId = :userId")
    List<String> findSkillsByUserId(int userId);


    @Modifying
    @Transactional
    @Query(value = "DELETE us FROM user_skills us JOIN allowed_skills a_skill ON us.skill_id = a_skill.skill_id WHERE us.user_id = :userId AND a_skill.skill_name = :skillName", nativeQuery = true)
    void deleteUserSkillBySkillName(int userId, String skillName);
}
