package com.telusko.SpringSecEx.Service;

import com.telusko.SpringSecEx.Entity.AllowedSkill;
import com.telusko.SpringSecEx.Entity.UserSkill;
import com.telusko.SpringSecEx.repository.AllowedSkillRepository;
import com.telusko.SpringSecEx.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private AllowedSkillRepository allowedSkillRepository;

    @Transactional
    public void addSkillToUser(int userId, String skillName) {
        AllowedSkill allowedSkill = allowedSkillRepository.findBySkillName(skillName);
        if (allowedSkill != null) {
            UserSkill userSkill = new UserSkill();
            userSkill.setUserId(userId);
            userSkill.setSkillId(allowedSkill.getSkillId());
            userSkill.setSkill(allowedSkill.getSkillName());
            userSkillRepository.save(userSkill);
        } else {
            throw new RuntimeException("Skill not found: " + skillName);
        }
    }


    public List<String> getSkillsByUserId(int userId) {
        return userSkillRepository.findSkillsByUserId(userId);
    }

    @Transactional
    public void deleteUserSkill(int userId, String skillName) {
        userSkillRepository.deleteUserSkillBySkillName(userId, skillName);
    }
}
