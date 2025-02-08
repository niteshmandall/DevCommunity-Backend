package com.telusko.SpringSecEx.Controller;


import com.telusko.SpringSecEx.Entity.LoginRequest;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/test")
    public String test() {
        return "admin call";
    }
}
