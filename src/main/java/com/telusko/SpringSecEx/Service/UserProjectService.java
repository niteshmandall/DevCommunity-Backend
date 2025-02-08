package com.telusko.SpringSecEx.Service;


import com.telusko.SpringSecEx.DTO.UserProjectDTO;
import com.telusko.SpringSecEx.Entity.UserProject;
import com.telusko.SpringSecEx.repository.UserProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProjectService {

    @Autowired
    private UserProjectRepository userProjectRepository;

    // Fetch all projects for a specific user
    public List<UserProject> getUserProjectsByUserId(Integer userId) {
        return userProjectRepository.findByIdUserId(userId);
    }

    // Fetch all users for a specific project
    public List<UserProject> getUserProjectsByProjectId(Long projectId) {
        return userProjectRepository.findByIdProjectId(projectId);
    }

    public List<UserProjectDTO> getAllUserProjects(Integer userId) {
        return userProjectRepository.findByIdUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserProjectDTO convertToDTO(UserProject userProject) {
        UserProjectDTO dto = new UserProjectDTO();
//        dto.setUserId(userProject.getId().getUserId());  // Map userId
        dto.setProjectId(userProject.getId().getProjectId());  // Map projectId
        dto.setRole(userProject.getRole().toString());
        dto.setAssignedAt(userProject.getAssignedAt());
        return dto;
    }

    @Transactional
    public void assignUserToProject(Integer userId, Long projectId) {
        // Add the user to the project with role 'contributor'
        userProjectRepository.addUserToProject(userId, projectId, "contributor");
    }
}
