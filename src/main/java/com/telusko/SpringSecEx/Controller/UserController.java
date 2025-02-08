package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.DTO.UserProfileDTO;
import com.telusko.SpringSecEx.DTO.UserRegistrationDTO;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.Service.UserProfileService;
import com.telusko.SpringSecEx.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/register")
    public Users registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO);
    }






}
