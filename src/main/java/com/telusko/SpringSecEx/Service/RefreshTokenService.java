package com.telusko.SpringSecEx.Service;


import com.telusko.SpringSecEx.Entity.RefreshToken;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.repository.RefreshTokenRepository;
import com.telusko.SpringSecEx.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    public long refreshTokenValidity = 1000 * 60 * 60 * 5;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepo userRepo;

    public RefreshToken createRefreshToken(String username) {

        Users user = userRepo.findByUsername(username);
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(userRepo.findByUsername(username))
                    .build();
        } else {
            refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken);

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }


    public RefreshToken verifyRefreshToken(String refreshToken)  {

        RefreshToken refreshTokenOb = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshTokenOb.getExpiry().compareTo(Instant.now()) < 0) {

            refreshTokenRepository.delete(refreshTokenOb);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshTokenOb;
    }

    public void deleteByUsername(String username) {
        Users user = userRepo.findByUsername(username);
        if (user != null) {
            RefreshToken refreshToken = user.getRefreshToken();
            if (refreshToken != null) {
                refreshTokenRepository.delete(refreshToken);
            }
        }
    }

}
