package com.telusko.SpringSecEx.DTO;

import lombok.Data;


@Data
public class DeleteWorkExperienceDTO {

    private String company;
    private String position;

    public DeleteWorkExperienceDTO(String company, String position) {
        this.company = company;
        this.position = position;
    }

}

