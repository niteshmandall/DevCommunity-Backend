package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.DTO.AddWorkExperienceDTO;
import com.telusko.SpringSecEx.DTO.DeleteWorkExperienceDTO;
import com.telusko.SpringSecEx.DTO.WorkExperienceDTO;
import com.telusko.SpringSecEx.Service.JWTService;
import com.telusko.SpringSecEx.Service.UserService;
import com.telusko.SpringSecEx.Service.UserWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-experience")
public class UserWorkExperienceController {

    @Autowired
    private UserWorkExperienceService userWorkExperienceService;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @GetMapping("list")
    public List<WorkExperienceDTO> getWorkExperience(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();
        return userWorkExperienceService.getWorkExperienceByUserId(userId);
    }


    @PostMapping("/add")
    public String addWorkExperience(@RequestHeader("Authorization") String token, @RequestBody AddWorkExperienceDTO addWorkExperienceDTO) {

        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();
        userWorkExperienceService.addWorkExperience(userId ,addWorkExperienceDTO);
        return "Work experience added successfully!";
    }

    @DeleteMapping("/delete")
    public String deleteWorkExperience(@RequestHeader("Authorization") String token, @RequestBody DeleteWorkExperienceDTO deleteWorkExperienceDTO) {

        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        int userId = userService.findByUsername(username).getUserId();
        String company = deleteWorkExperienceDTO.getCompany();
        String position = deleteWorkExperienceDTO.getPosition();

        userWorkExperienceService.deleteWorkExperience(userId, company, position);
        return "Work experience deleted successfully!";
    }
}
