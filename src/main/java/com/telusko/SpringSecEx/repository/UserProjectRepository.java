package com.telusko.SpringSecEx.repository;



import com.telusko.SpringSecEx.Entity.UserProject;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProject.UserProjectId> {

    // Custom query to fetch user projects by userId
    List<UserProject> findByIdUserId(Integer userId);

    // Custom query to fetch user projects by projectId
    List<UserProject> findByIdProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_projects (user_id, project_id, role, assigned_at) " +
            "VALUES (:userId, :projectId, :role, NOW())", nativeQuery = true)
    void addUserToProject(int userId, Long projectId, String role);



    @Modifying
    @Query(value = "INSERT INTO user_projects (user_id, project_id, role, assigned_at) " +
            "VALUES (:userId, :projectId, :role, NOW())", nativeQuery = true)
    void addUserToProject(@Param("userId") Integer userId,
                          @Param("projectId") Long projectId,
                          @Param("role") String role);


}
