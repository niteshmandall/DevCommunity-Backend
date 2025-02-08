package com.telusko.SpringSecEx.DTO;

import com.telusko.SpringSecEx.Entity.RequestedProject.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RequestedUserDTO {
    private int userId;         // Matches u.userId in query
    private String username;    // Matches u.username in query
    private String email;       // Matches u.email in query
    private RequestStatus requestStatus;  // Matches rp.requestStatus in query
    private Date requestedAt;   // Matches rp.requestedAt in query
    private int requestId;      // Matches rp.requestId in query
}
