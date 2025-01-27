package com.project.id.project.application.DTOs.phonenumbers;

import com.project.id.project.core.Id;
import jakarta.persistence.Column;

public class AdditionalPhoneNumberDTO {
    private String phoneNumber;

    public AdditionalPhoneNumberDTO() {
    }

    public AdditionalPhoneNumberDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
