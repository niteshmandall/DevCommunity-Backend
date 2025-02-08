package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.DTO.WorkExperienceDTO;
import com.telusko.SpringSecEx.Entity.UserWorkExperience;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserWorkExperienceRepository extends JpaRepository<UserWorkExperience, Integer> {
    List<UserWorkExperience> findByUserId(int userId);


    @Query("SELECT new com.telusko.SpringSecEx.DTO.WorkExperienceDTO(uwe.company, uwe.position, uwe.duration) " +
            "FROM UserWorkExperience uwe WHERE uwe.userId = :userId")
    List<WorkExperienceDTO> findWorkExperienceByUserId(int userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM UserWorkExperience uwe WHERE uwe.userId = :userId AND uwe.company = :company AND uwe.position = :position")
    void deleteByUserIdAndCompanyAndPosition(int userId, String company, String position);
}

