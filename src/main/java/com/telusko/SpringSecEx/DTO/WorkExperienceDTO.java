package com.telusko.SpringSecEx.DTO;

import lombok.Data;

@Data
public class WorkExperienceDTO {

    private String company;
    private String position;
    private String duration;

    public WorkExperienceDTO(String company, String position, String duration) {
        this.company = company;
        this.position = position;
        this.duration = duration;
    }

}

