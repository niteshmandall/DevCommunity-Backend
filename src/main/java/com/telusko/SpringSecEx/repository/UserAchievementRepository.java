package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.Entity.UserAchievement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Integer> {
    List<UserAchievement> findByUserId(int userId);


    @Transactional
    @Modifying
    @Query("DELETE FROM UserAchievement ua WHERE ua.userId = :userId AND ua.achievement = :achievement")
    void deleteByUserIdAndAchievement(@Param("userId") int userId, @Param("achievement") String achievement);
}

