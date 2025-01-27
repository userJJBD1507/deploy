package com.project.id.project.application.DTOs.phonenumbers;

import com.project.id.project.core.Id;
import jakarta.persistence.Column;

public class PhoneNumberDTO {
    private String phoneNumber;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
