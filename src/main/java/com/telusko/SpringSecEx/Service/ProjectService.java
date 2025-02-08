package com.telusko.SpringSecEx.Service;


import com.telusko.SpringSecEx.DTO.ProjectDTO;
import com.telusko.SpringSecEx.models.Project;
import com.telusko.SpringSecEx.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ProjectDTO> getProjectsByIds(List<Long> projectIds) {
        return projectRepository.findAllById(projectIds).stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    // Convert Project entity to ProjectDTO
    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setProjectId(project.getProjectId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setTechStack(project.getTechStack());
        dto.setDifficulty(project.getDifficulty());
        dto.setStatus(project.getStatus());
        dto.setLeadName(project.getLead().getUsername());  // Only set lead name, no other details
        return dto;
    }


    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).get();
    }

    public ProjectDTO getProjectById(Long projectId) {
        return convertToDTO(findById(projectId));
    }
}


