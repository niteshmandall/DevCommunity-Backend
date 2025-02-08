package com.telusko.SpringSecEx.Service;

import com.telusko.SpringSecEx.DTO.AddWorkExperienceDTO;
import com.telusko.SpringSecEx.DTO.WorkExperienceDTO;
import com.telusko.SpringSecEx.Entity.UserWorkExperience;
import com.telusko.SpringSecEx.repository.UserWorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWorkExperienceService {

    @Autowired
    private UserWorkExperienceRepository userWorkExperienceRepository;

    public List<WorkExperienceDTO> getWorkExperienceByUserId(int userId) {
        return userWorkExperienceRepository.findWorkExperienceByUserId(userId);
    }

    public void addWorkExperience(int userId, AddWorkExperienceDTO addWorkExperienceDTO) {
        UserWorkExperience workExperience = new UserWorkExperience();
        workExperience.setUserId(userId);
        workExperience.setCompany(addWorkExperienceDTO.getCompany());
        workExperience.setPosition(addWorkExperienceDTO.getPosition());
        workExperience.setDuration(addWorkExperienceDTO.getDuration());

        // Save the work experience to the database
        userWorkExperienceRepository.save(workExperience);
    }

    public void deleteWorkExperience(int userId, String company, String position) {
        userWorkExperienceRepository.deleteByUserIdAndCompanyAndPosition(userId, company, position);
    }


}
