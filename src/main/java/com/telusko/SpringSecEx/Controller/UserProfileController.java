package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.DTO.LinkedinLinkRequest;
import com.telusko.SpringSecEx.DTO.ProfileLinksRequest;
import com.telusko.SpringSecEx.DTO.UserProfileDTO;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.Service.*;
import com.telusko.SpringSecEx.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserSkillService userSkillService;
    @Autowired
    private UserAchievementService userAchievementService;

    @Autowired
    private UserWorkExperienceService userWorkExperienceService;

    @PutMapping("/github-link")
    public ResponseEntity<String> updateGithubLink(@RequestHeader("Authorization") String token, @RequestBody ProfileLinksRequest profileLinksRequest) {
        String tokenValue = token.substring(7);

        String username = jwtService.extractUserName(tokenValue);

        int userId = userService.findByUsername(username).getUserId();

        userProfileService.updateGithubLink(userId, profileLinksRequest.getGithubLink());
        return ResponseEntity.ok("GitHub link updated successfully");
    }

    @GetMapping("/github-link")
    public ResponseEntity<ProfileLinksRequest> getGithubLink(@RequestHeader("Authorization") String token) {
        String tokenValue = token.substring(7);
        String username = jwtService.extractUserName(tokenValue);
        int userId = userService.findByUsername(username).getUserId();
        String githubLink = userProfileService.getGithubLinkByUserId(userId);

        ProfileLinksRequest profileLinksRequest = new ProfileLinksRequest();
        profileLinksRequest.setGithubLink(githubLink);
        
        return ResponseEntity.ok(profileLinksRequest);
    }



    @PutMapping("/linkedin-link")
    public ResponseEntity<String> updateLinkedinLink(@RequestHeader("Authorization") String token, @RequestBody LinkedinLinkRequest linkedinLinkRequest) {
        String tokenValue = token.substring(7);

        String username = jwtService.extractUserName(tokenValue);

        int userId = userService.findByUsername(username).getUserId();

        userProfileService.updateLinkedinLink(userId, linkedinLinkRequest.getLinkedinLink());
        return ResponseEntity.ok("Linkedin link updated successfully");
    }


    @GetMapping("/linkedin-link")
    public ResponseEntity<LinkedinLinkRequest> getLinkedinLink(@RequestHeader("Authorization") String token) {
        String tokenValue = token.substring(7);
        String username = jwtService.extractUserName(tokenValue);
        int userId = userService.findByUsername(username).getUserId();
        String linkedinLink = userProfileService.getLinkedinLinkByUserId(userId);

        LinkedinLinkRequest linkedinLinkRequest = new LinkedinLinkRequest();
        linkedinLinkRequest.setLinkedinLink(linkedinLink);

        return ResponseEntity.ok(linkedinLinkRequest);
    }

    @GetMapping()
    public ResponseEntity<UserProfileDTO> getProfile(@RequestParam("userId") Integer userId) {
        System.out.println("Profile call requested for userId: " + userId);

        // Fetch user profile data
        Users user = userRepo.findByUserId(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setName(user.getUsername());
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setProfilePicture(userProfileService.getProfileLinkByUserId(userId));
        userProfileDTO.setGithubLink(userProfileService.getGithubLinkByUserId(userId));
        userProfileDTO.setLinkedinLink(userProfileService.getLinkedinLinkByUserId(userId));
        userProfileDTO.setSkills(userSkillService.getSkillsByUserId(userId));
        userProfileDTO.setAchievements(userAchievementService.getAchievementsByUserId(userId));
        userProfileDTO.setWorkExperiences(userWorkExperienceService.getWorkExperienceByUserId(userId));

        return ResponseEntity.ok(userProfileDTO);
    }


}
