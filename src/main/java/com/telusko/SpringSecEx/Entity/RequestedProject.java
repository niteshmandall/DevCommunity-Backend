package com.telusko.SpringSecEx.Entity;

import com.telusko.SpringSecEx.models.Project;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "requested_projects")
public class RequestedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", columnDefinition = "ENUM('pending', 'approved', 'rejected')")
    private RequestStatus requestStatus;

    @Column(name = "requested_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestedAt;

    public enum RequestStatus {
        pending,
        approved,
        rejected
    }
}
