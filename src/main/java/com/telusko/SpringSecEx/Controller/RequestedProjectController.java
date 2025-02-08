package com.telusko.SpringSecEx.Controller;


import com.telusko.SpringSecEx.DTO.*;
import com.telusko.SpringSecEx.Entity.RequestedProject;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.Service.JWTService;
import com.telusko.SpringSecEx.Service.ProjectService;
import com.telusko.SpringSecEx.Service.RequestedProjectService;
import com.telusko.SpringSecEx.Service.UserService;
import com.telusko.SpringSecEx.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requested-projects")
public class RequestedProjectController {

    @Autowired
    private RequestedProjectService requestedProjectService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;


    @GetMapping()
    public ResponseEntity<List<ProjectWithRequestStatusDTO>> getRequestedProjects(@RequestHeader("Authorization") String token) {
        // Extract username from JWT token
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);

        // Find user by username
        Users user = userService.findByUsername(username);

        // Get projects along with request status
        List<ProjectWithRequestStatusDTO> projectsWithStatus = requestedProjectService.getRequestedProjectsWithStatus(user.getUserId());

        return ResponseEntity.ok(projectsWithStatus);
    }


    @PostMapping
    public ResponseEntity<RequestedProject> requestProject(@RequestHeader("Authorization") String token, @RequestBody RequestedProjectDTO requestedProjectDTO) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        Users user = userService.findByUsername(username);


        RequestedProject requestedProject = new RequestedProject();
        requestedProject.setUser(user);
        requestedProject.setProject(projectService.findById(requestedProjectDTO.getProjectId()));
        requestedProject.setRequestStatus(RequestedProject.RequestStatus.pending);
        requestedProject.setRequestedAt(new Date());

        RequestedProject savedRequest = requestedProjectService.saveRequestedProject(requestedProject);
        return ResponseEntity.ok(savedRequest);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<RequestedUserDTO>> getRequestedUsers(@RequestHeader("Authorization") String token,@PathVariable Long projectId) {
        List<RequestedUserDTO> requestedUsers = requestedProjectService.getUsersByProjectId(projectId);


        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);

        ProjectDTO projectDTO = projectService.getProjectById(projectId);

        if(projectDTO.getLeadName().equals(username)) {
            return ResponseEntity.ok(requestedUsers);
        }

        System.out.println("requested projects");
        return ResponseEntity.status(403).body(null);
    }



    @PostMapping("/updateRequestStatus")
    public ResponseEntity<String> updateRequestStatus(
            @RequestHeader("Authorization") String token,
            @RequestBody RequestApprovalDTO requestApprovalDTO) {

        // Extract username from JWT
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtService.extractUserName(jwtToken);

        // Fetch the requested project by ID
        RequestedProjectDTO requestedProjectDTO = requestedProjectService.getRequestedProjectById(requestApprovalDTO.getRequestId());

        // Get the project lead's username
        String projectLeadUsername = projectService.getProjectById(requestedProjectDTO.getProjectId()).getLeadName();

        // Check if the user is the project lead
        if (!username.equals(projectLeadUsername)) {
            // If not lead, return a 403 Forbidden response
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this request.");
        }

        // Determine new request status (approved or rejected)
        RequestedProject.RequestStatus newStatus = requestApprovalDTO.isApprove() == 1
                ? RequestedProject.RequestStatus.approved
                : RequestedProject.RequestStatus.rejected;

        // Update the request status
        requestedProjectService.updateRequestStatus(requestApprovalDTO.getRequestId(), newStatus);

        // Return success response
        return ResponseEntity.ok("Request status updated successfully.");
    }

    @GetMapping("/getRequestedProjects")
    public RequestedProjectDTO getRequestedProjects(@RequestBody RequestApprovalDTO requestApprovalDTO) {
        // Fetch and return the requested project details without updating the status
        return requestedProjectService.getRequestedProjectById(requestApprovalDTO.getRequestId());
    }


}
