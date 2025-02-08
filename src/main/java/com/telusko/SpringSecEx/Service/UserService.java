package com.telusko.SpringSecEx.Service;

import com.telusko.SpringSecEx.Custom_Exceptions.UserAlreadyExistsException;
import com.telusko.SpringSecEx.DTO.UserRegistrationDTO;
import com.telusko.SpringSecEx.Entity.LoginRequest;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users registerUser(UserRegistrationDTO registrationDTO) {
        // Check if the username already exists
        if (userRepo.findByUsername(registrationDTO.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        // Create a new user entity
        Users newUser = new Users();
        newUser.setUsername(registrationDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword())); // Hash the password
        newUser.setRole("USER"); // Default role, adjust as needed
        newUser.setActive(true); // Set user status as active

        return userRepo.save(newUser);
    }

    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public String verfiy(LoginRequest loginRequest) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(loginRequest.getUsername());
        }
        return "fail";
    }

    public String generateOTP(String email) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000000);
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    public Users registerUser(String username, String password, String email) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setActive(true);
        user.setEmail(email);

        return userRepo.save(user);
    }

    public void deleteOtp(String email) {
        otpStorage.remove(email);
    }
}
