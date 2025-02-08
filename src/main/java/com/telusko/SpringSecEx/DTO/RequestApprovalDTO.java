package com.telusko.SpringSecEx.DTO;

import lombok.Data;

@Data
public class RequestApprovalDTO {
    private int requestId;
    private int projectId;
    private int isApprove; // true for approve, false for reject

    public int isApprove() {
        return isApprove;
    }

    // Getters and Setters
}
