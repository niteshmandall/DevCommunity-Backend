package com.telusko.SpringSecEx.Service;

import com.telusko.SpringSecEx.DTO.UserProfileDTO;
import com.telusko.SpringSecEx.Entity.*;
import com.telusko.SpringSecEx.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UserWorkExperienceRepository userWorkExperienceRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public void updateGithubLink(int userId, String githubLink) {
        userProfileRepository.updateGithubLink(userId, githubLink);
    }


    public void createDefaultUserProfile(int userId) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);  // Assuming userId is the foreign key from the users table
        userProfile.setProfilePicture("");  // Set empty default values
        userProfile.setGithubLink("");
        userProfile.setLinkedinLink("");

        userProfileRepository.save(userProfile);  // Save the default profile
    }

    @Transactional
    public void updateLinkedinLink(int userId, String linkedinLink) {
        userProfileRepository.updateLinkedinLink(userId, linkedinLink);
    }

    public String getGithubLinkByUserId(int userId) {
        return userProfileRepository.findGithubLinkByUserId(userId);
    }

    public String getLinkedinLinkByUserId(int userId) {
        return userProfileRepository.findLinkedinLinkByUserId(userId);
    }

    public String getProfileLinkByUserId(int userId) {
        return userProfileRepository.findProfileLinkByUserId(userId);
    }
}

