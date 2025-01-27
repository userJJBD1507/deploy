package com.project.id.project.application.DTOs.documents;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;

public class PassportDTO {
    private String photo;
    private String seriesAndNumber;
    private String surname;
    private String name;
    private String patronymic;
    private String birthdate;
    private String birthplace;
    private Gender gender;
    private String whoIssued;
    private String divisionCode;
    private String issueDate;
    private String registrationAddress;

    public PassportDTO() {
    }

    public PassportDTO(
                       String photo,
                       String seriesAndNumber,
                       String surname,
                       String name,
                       String patronymic,
                       String birthdate,
                       String birthplace,
                       Gender gender,
                       String whoIssued,
                       String divisionCode,
                       String issueDate,
                       String registrationAddress) {
        this.photo = photo;
        this.seriesAndNumber = seriesAndNumber;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
        this.gender = gender;
        this.whoIssued = whoIssued;
        this.divisionCode = divisionCode;
        this.issueDate = issueDate;
        this.registrationAddress = registrationAddress;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getWhoIssued() {
        return whoIssued;
    }

    public void setWhoIssued(String whoIssued) {
        this.whoIssued = whoIssued;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }
}
