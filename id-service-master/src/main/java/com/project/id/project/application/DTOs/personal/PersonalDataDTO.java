package com.project.id.project.application.DTOs.personal;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;
import jakarta.persistence.Column;

public class PersonalDataDTO {
    private String avatar;
    private String invocation;
    private String name;
    private String surname;
    private Gender gender;
    private String birthdate;
    private String locality;
    private String timeZone;

    public PersonalDataDTO() {
    }

    public PersonalDataDTO(
                           String avatar,
                           String invocation,
                           String name,
                           String surname, Gender gender,
                           String birthdate, String locality, String timeZone) {
        this.avatar = avatar;
        this.invocation = invocation;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.locality = locality;
        this.timeZone = timeZone;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInvocation() {
        return invocation;
    }

    public void setInvocation(String invocation) {
        this.invocation = invocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "PersonalDataDTO{" +
                "avatar='" + avatar + '\'' +
                ", invocation='" + invocation + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", birthdate='" + birthdate + '\'' +
                ", locality='" + locality + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
