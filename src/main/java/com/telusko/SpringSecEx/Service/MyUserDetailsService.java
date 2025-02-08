package com.telusko.SpringSecEx.Service;

import com.telusko.SpringSecEx.Entity.UserPrincipal;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }

    public String getUserRoleByUsername(String username) {
        Users user = repo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user.getRole();
    }
}
