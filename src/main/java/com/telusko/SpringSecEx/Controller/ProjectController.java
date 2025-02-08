package com.telusko.SpringSecEx.Controller;

import com.telusko.SpringSecEx.DTO.ProjectDTO;
import com.telusko.SpringSecEx.DTO.UserProjectDTO;
import com.telusko.SpringSecEx.Entity.UserProject;
import com.telusko.SpringSecEx.Entity.Users;
import com.telusko.SpringSecEx.Service.JWTService;
import com.telusko.SpringSecEx.Service.ProjectService;
import com.telusko.SpringSecEx.Service.UserProjectService;
import com.telusko.SpringSecEx.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    UserProjectService userProjectService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/getuserprojects")
    public ResponseEntity<?> getUserProjects(@RequestHeader("Authorization") String token) {
        System.out.println("get User Project called");

        // Extract username from the JWT token
        String jwtToken = token.substring(7);
        String username = jwtService.extractUserName(jwtToken);
        Users user = userService.findByUsername(username);
        Integer id = user.getUser_id();

        // Fetch all user-project relationships
        List<UserProjectDTO> userProjects = userProjectService.getAllUserProjects(id);

        // Separate the project IDs by role
        List<Long> contributorProjectIds = new ArrayList<>();
        List<Long> leadProjectIds = new ArrayList<>();

        userProjects.forEach(project -> {
            if ("contributor".equalsIgnoreCase(project.getRole())) {
                contributorProjectIds.add(project.getProjectId());
            } else if ("lead".equalsIgnoreCase(project.getRole())) {
                leadProjectIds.add(project.getProjectId());
            }
        });

        // Fetch the actual project details by project IDs
        List<ProjectDTO> contributorProjects = fetchProjectsByIds(contributorProjectIds);
        List<ProjectDTO> leadProjects = fetchProjectsByIds(leadProjectIds);

        // Create a response that separates the projects by role
        Map<String, List<ProjectDTO>> response = new HashMap<>();
        response.put("contributorProjects", contributorProjects);
        response.put("leadProjects", leadProjects);

        return ResponseEntity.ok(response);
    }



    public List<ProjectDTO> fetchProjectsByIds(List<Long> projectIds) {
        return projectService.getProjectsByIds(projectIds);
    }

    @PostMapping("/addProject")
    public void addProject() {

        userProjectService.assignUserToProject(3, 37L);
    }


}
