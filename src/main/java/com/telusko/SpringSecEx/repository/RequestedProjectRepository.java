package com.telusko.SpringSecEx.repository;

import com.telusko.SpringSecEx.DTO.RequestedUserDTO;
import com.telusko.SpringSecEx.Entity.RequestedProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestedProjectRepository extends JpaRepository<RequestedProject, Integer> {

    // Use the updated userId field from Users entity
    List<RequestedProject> findByUser_UserId(int userId);

    boolean existsByUser_UserIdAndProject_ProjectId(Integer userId, Long projectId);

    @Query("SELECT new com.telusko.SpringSecEx.DTO.RequestedUserDTO(u.userId, u.username, u.email, rp.requestStatus, rp.requestedAt, rp.requestId) " +
            "FROM RequestedProject rp JOIN rp.user u WHERE rp.project.projectId = :projectId")
    List<RequestedUserDTO> findUsersByProjectId(@Param("projectId") Long projectId);
}
