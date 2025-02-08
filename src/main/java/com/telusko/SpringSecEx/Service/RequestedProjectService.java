package com.telusko.SpringSecEx.Service;


import com.telusko.SpringSecEx.DTO.ProjectWithRequestStatusDTO;
import com.telusko.SpringSecEx.DTO.RequestApprovalDTO;
import com.telusko.SpringSecEx.DTO.RequestedProjectDTO;
import com.telusko.SpringSecEx.DTO.RequestedUserDTO;
import com.telusko.SpringSecEx.Entity.RequestedProject;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.repository.RequestedProjectRepository;
import com.telusko.SpringSecEx.repository.UserProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestedProjectService {

    @Autowired
    private RequestedProjectRepository requestedProjectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    private UserProjectService userProjectService;

    public RequestedProject saveRequestedProject(RequestedProject requestedProject) {
        return requestedProjectRepository.save(requestedProject);
    }

    public List<RequestedProject> getRequestedProjectsByUserId(int userId) {
        return requestedProjectRepository.findByUser_UserId(userId);
    }

    public List<RequestedProjectDTO> getRequestedProjects(int userId) {
        List<RequestedProject> requestedProjects = getRequestedProjectsByUserId(userId);

        return requestedProjects.stream().map(project -> {
            RequestedProjectDTO dto = new RequestedProjectDTO();
            dto.setRequestId(project.getRequestId());
            dto.setUserId(project.getUser().getUser_id());
            dto.setProjectId(project.getProject().getProjectId());
            dto.setRequestStatus(project.getRequestStatus().name());  // Enum to String
            dto.setRequestedAt(project.getRequestedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    public boolean hasUserRequestedProject(Integer userId, Long projectId) {
        return requestedProjectRepository.existsByUser_UserIdAndProject_ProjectId(userId, projectId);
    }

    public List<RequestedUserDTO> getUsersByProjectId(Long projectId) {
        return requestedProjectRepository.findUsersByProjectId(projectId);
    }

    public List<ProjectWithRequestStatusDTO> getRequestedProjectsWithStatus(int userId) {
        // Fetch requested projects for the user
        List<RequestedProject> requestedProjects = requestedProjectRepository.findByUser_UserId(userId);

        // Map each requested project into ProjectWithRequestStatusDTO
        List<ProjectWithRequestStatusDTO> projectDTOs = new ArrayList<>();

        requestedProjects.forEach(requestedProject -> {
            ProjectWithRequestStatusDTO projectDTO = new ProjectWithRequestStatusDTO(
                    requestedProject.getProject().getProjectId(),
                    requestedProject.getProject().getTitle(),
                    requestedProject.getProject().getDescription(),
                    requestedProject.getProject().getTechStack(),
                    requestedProject.getProject().getDifficulty(),
                    requestedProject.getProject().getStatus(),
                    requestedProject.getProject().getLead().getUsername(),  // Only set lead name, no other details
                    requestedProject.getRequestStatus()  // Include request status
            );
            projectDTOs.add(projectDTO);
        });

        return projectDTOs;
    }

    @Transactional
    public boolean approveOrRejectRequest(RequestApprovalDTO requestApprovalDTO) {
        Optional<RequestedProject> optionalRequest = requestedProjectRepository.findById(requestApprovalDTO.getRequestId());

        if (optionalRequest.isPresent()) {
            RequestedProject requestedProject = optionalRequest.get();
            Integer userId = requestedProject.getUser().getUserId();
            Long projectId = requestedProject.getProject().getProjectId();

            // If it's approved, update the request status and add to user_projects
            if (requestApprovalDTO.isApprove() == 1) {
                // 1. Update the requested project status to approved
                requestedProject.setRequestStatus(RequestedProject.RequestStatus.approved);
                requestedProjectRepository.save(requestedProject); // Save the updated status

                // 2. Insert the approved project into the user_projects table
                userProjectService.assignUserToProject(userId, projectId);
                System.out.println("adding to projects");


                return true;
            } else {
                // Set status to rejected
                requestedProject.setRequestStatus(RequestedProject.RequestStatus.rejected);
                requestedProjectRepository.save(requestedProject);
                return false;
            }
        }
        return false;
    }



    // Method to fetch a RequestedProjectDTO by requestId
    public RequestedProjectDTO getRequestedProjectById(int requestId) {
        Optional<RequestedProject> requestedProjectOptional = requestedProjectRepository.findById(requestId);
        if (requestedProjectOptional.isPresent()) {
            RequestedProject requestedProject = requestedProjectOptional.get();

            // Convert the entity to DTO
            RequestedProjectDTO dto = new RequestedProjectDTO();
            dto.setRequestId(requestedProject.getRequestId());
            dto.setUserId(requestedProject.getUser().getUser_id());
            dto.setProjectId(requestedProject.getProject().getProjectId());
            dto.setRequestStatus(requestedProject.getRequestStatus().name());
            dto.setRequestedAt(requestedProject.getRequestedAt());

            return dto;
        }
        throw new RuntimeException("Requested Project not found");
    }

    // Method to update the request status in the database
    @Transactional
    public RequestedProjectDTO updateRequestStatus(int requestId, RequestedProject.RequestStatus newStatus) {
        Optional<RequestedProject> requestedProjectOptional = requestedProjectRepository.findById(requestId);
        if (requestedProjectOptional.isPresent()) {
            RequestedProject requestedProject = requestedProjectOptional.get();
            requestedProject.setRequestStatus(newStatus);
            requestedProjectRepository.save(requestedProject); // Save the updated status

            // Convert updated entity to DTO
            RequestedProjectDTO updatedDTO = new RequestedProjectDTO();
            updatedDTO.setRequestId(requestedProject.getRequestId());
            updatedDTO.setUserId(requestedProject.getUser().getUser_id());
            updatedDTO.setProjectId(requestedProject.getProject().getProjectId());
            updatedDTO.setRequestStatus(requestedProject.getRequestStatus().name());
            updatedDTO.setRequestedAt(requestedProject.getRequestedAt());

            return updatedDTO;
        }
        throw new RuntimeException("Requested Project not found");
    }

}
