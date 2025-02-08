package com.telusko.SpringSecEx.Service;


import com.telusko.SpringSecEx.Entity.UserAchievement;
import com.telusko.SpringSecEx.repository.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAchievementService {

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    public List<String> getAchievementsByUserId(int userId) {
        List<UserAchievement> achievements = userAchievementRepository.findByUserId(userId);
        // Return only the achievement names
        return achievements.stream().map(UserAchievement::getAchievement).toList();
    }

    public void addAchievement(int userId, String achievement) {
        UserAchievement newAchievement = new UserAchievement();
        newAchievement.setUserId(userId);
        newAchievement.setAchievement(achievement);

        // Save the new achievement to the database
        userAchievementRepository.save(newAchievement);
    }

    public void deleteAchievement(int userId, String achievement) {
        userAchievementRepository.deleteByUserIdAndAchievement(userId, achievement);
    }
}
