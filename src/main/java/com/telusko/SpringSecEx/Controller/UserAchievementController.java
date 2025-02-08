package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.Service.JWTService;
import com.telusko.SpringSecEx.Service.UserAchievementService;
import com.telusko.SpringSecEx.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class UserAchievementController {

    @Autowired
    private UserAchievementService userAchievementService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<String> getUserAchievements(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId(); // get user
        return userAchievementService.getAchievementsByUserId(userId);
    }


    @PostMapping("/add")
    public void addAchievement(@RequestHeader("Authorization") String token, @RequestParam String achievement) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();
        userAchievementService.addAchievement(userId, achievement);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAchievement(@RequestHeader("Authorization") String token,
            @RequestParam String achievement) {

        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();

        userAchievementService.deleteAchievement(userId, achievement);
        return ResponseEntity.ok("Achievement deleted successfully.");
    }

}
