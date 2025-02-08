package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserId(int userId);

    @Modifying
    @Query("UPDATE UserProfile u SET u.githubLink = :githubLink WHERE u.userId = :userId")
    void updateGithubLink(@Param("userId") int userId, @Param("githubLink") String githubLink);


    @Modifying
    @Query("UPDATE UserProfile u SET u.linkedinLink = :linkedinLink WHERE u.userId = :userId")
    void updateLinkedinLink(@Param("userId") int userId, @Param("linkedinLink") String linkedinLink);


    @Query("SELECT up.githubLink FROM UserProfile up WHERE up.userId = :userId")
    String findGithubLinkByUserId(@Param("userId") int userId);

    @Query("SELECT up.linkedinLink FROM UserProfile up WHERE up.userId = :userId")
    String findLinkedinLinkByUserId(@Param("userId") int userId);

    @Query("SELECT up.profilePicture FROM UserProfile up WHERE up.userId = :userId")
    String findProfileLinkByUserId(@Param("userId") int userId);
}

