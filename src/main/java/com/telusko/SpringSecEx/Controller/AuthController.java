package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.DTO.OtpVerificationDTO;
import com.telusko.SpringSecEx.DTO.ProjectDTO;
import com.telusko.SpringSecEx.DTO.Registration;
import com.telusko.SpringSecEx.DTO.UserRegistrationDTO;
import com.telusko.SpringSecEx.Entity.LoginRequest;
import com.telusko.SpringSecEx.Entity.RefreshToken;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.Service.*;
import com.telusko.SpringSecEx.models.JwtResponse;
import com.telusko.SpringSecEx.models.RefreshTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/public")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JWTService jwtService;

    @Autowired
    EmailService emailService;

    @Autowired
    ProjectService projectService;
    @Autowired
    private UserProfileService userProfileService;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Users user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Return success response, e.g., token or success message
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    @PostMapping("/login2")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {

        System.out.println(loginRequest);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());
        String jwtToken = userService.verfiy(loginRequest);
        int userId = userService.findByUsername(loginRequest.getUsername()).getUserId();

        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(jwtToken)
                .refreshToken(refreshToken.getRefreshToken())
                .username(loginRequest.getUsername())
                .userId(userId)
                .email(userService.findByUsername(loginRequest.getUsername()).getEmail())
                .build();


        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

//    @PostMapping("/register")
//    public Users register(@RequestBody UserRegistrationDTO registrationDTO) {
//        System.out.println(registrationDTO);
//        return userService.registerUser(registrationDTO);
//    }

    @GetMapping("/test")
    public String test() {
        System.out.println("connecton checking");
        return "public call";
    }


    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshJwtToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println(refreshTokenRequest);

        RefreshToken newRefreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        String jwtToken = jwtService.generateToken(newRefreshToken.getUser().getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("jwtToken", jwtToken);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody Registration registrationDTO) {

        String otp = userService.generateOTP(registrationDTO.getEmail());

        emailService.sendOtpEmail(registrationDTO.getEmail(), otp);


        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationDTO otpDTO) {
        boolean isVerified = userService.verifyOtp(otpDTO.getEmail(), otpDTO.getOtp());

        if (isVerified) {
            // Register the user
            userService.registerUser(otpDTO.getUsername(), otpDTO.getPassword(), otpDTO.getEmail());
            int userID = userService.findByUsername(otpDTO.getUsername()).getUserId();
            userProfileService.createDefaultUserProfile(userID);

            // Delete the OTP after successful verification
            userService.deleteOtp(otpDTO.getEmail());

            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);

        refreshTokenService.deleteByUsername(username);



        return ResponseEntity.ok("User logged out successfully");
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }



}
