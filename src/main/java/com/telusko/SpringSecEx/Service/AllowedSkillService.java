package com.telusko.SpringSecEx.Service;

import com.telusko.SpringSecEx.Entity.AllowedSkill;
import com.telusko.SpringSecEx.repository.AllowedSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllowedSkillService {

    @Autowired
    private AllowedSkillRepository allowedSkillRepository;

    public List<AllowedSkill> getAllAllowedSkills() {
        return allowedSkillRepository.findAll();
    }
}

