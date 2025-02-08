package com.telusko.SpringSecEx.repository;


import com.telusko.SpringSecEx.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Additional query methods if required
}

