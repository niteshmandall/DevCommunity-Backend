package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.Entity.AllowedSkill;
import com.telusko.SpringSecEx.Service.AllowedSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/allowed-skills")
public class AllowedSkillController {

    @Autowired
    private AllowedSkillService allowedSkillService;

    @GetMapping("/all")
    public ResponseEntity<List<AllowedSkill>> getAllAllowedSkills() {
        List<AllowedSkill> allowedSkills = allowedSkillService.getAllAllowedSkills();
        return new ResponseEntity<>(allowedSkills, HttpStatus.OK);
    }
}

