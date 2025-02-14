package com.telusko.SpringSecEx.Controller;


import com.telusko.SpringSecEx.DTO.SkillRequest;
import com.telusko.SpringSecEx.Service.JWTService;
import com.telusko.SpringSecEx.Service.UserService;
import com.telusko.SpringSecEx.Service.UserSkillService;
import com.telusko.SpringSecEx.models.RefreshTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public void addSkillToUser(@RequestHeader("Authorization") String token, @RequestBody SkillRequest skillRequest) {
        System.out.println("Skill Additon Request");
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();

        String skillName = skillRequest.getSkill();
        System.out.println(userId + " " + skillName);
        userSkillService.addSkillToUser(userId, skillName);
    }

    @GetMapping("/skills")
    public List<String> getSkillsByUserId(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();

        return userSkillService.getSkillsByUserId(userId);
    }


    @DeleteMapping("/delete-skill")
    public void deleteUserSkill(@RequestHeader("Authorization") String token, @RequestBody SkillRequest skillRequest) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();
        String skillName = skillRequest.getSkill();
        userSkillService.deleteUserSkill(userId, skillName);
    }
}

