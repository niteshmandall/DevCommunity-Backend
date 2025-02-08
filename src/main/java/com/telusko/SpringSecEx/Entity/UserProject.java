package com.telusko.SpringSecEx.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_projects")
@Getter
@Data
@NoArgsConstructor
public class UserProject {

    @EmbeddedId
    private UserProjectId id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    public enum Role {
        lead, contributor
    }

    @Embeddable
    public static class UserProjectId implements Serializable {

        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "project_id")
        private Long projectId;

        public Integer getUserId() {
            return userId;
        }

        public Long getProjectId() {
            return projectId;
        }

        // default constructor, equals, and hashCode methods
    }
}
