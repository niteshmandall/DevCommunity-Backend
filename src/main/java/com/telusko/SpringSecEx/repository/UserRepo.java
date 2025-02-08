package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);

    Users findByUserId(int userId);
}
