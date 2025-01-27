package com.project.id.project.application.DTOs.emails;

import com.project.id.project.core.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;

public class AdditionalEmailDTO {
    private String email;

    public AdditionalEmailDTO() {
    }

    public AdditionalEmailDTO(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
