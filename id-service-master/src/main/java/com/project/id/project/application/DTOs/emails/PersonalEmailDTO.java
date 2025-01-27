package com.project.id.project.application.DTOs.emails;

import com.project.id.project.core.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;

public class PersonalEmailDTO {
    private String email;

    public PersonalEmailDTO() {
    }

    public PersonalEmailDTO(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
