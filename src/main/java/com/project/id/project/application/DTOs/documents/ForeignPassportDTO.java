package com.project.id.project.application.DTOs.documents;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;

public class ForeignPassportDTO {
    private String photo;
    private String surname;
    private String name;
    private String patronymic;
    private String birthdate;
    private Gender gender;
    private String number;
    private String citizenship;
    private String birthplace;
    private String expirationDate;
    private String whoIssued;
    private String issueDate;



    private String surnameLatin;
    private String nameLatin;
    private String citizenshipLatin;
    private String birthplaceLatin;

    public ForeignPassportDTO() {
    }

    public ForeignPassportDTO(
                              String photo,
                              String surname,
                              String name,
                              String patronymic,
                              String birthdate,
                              Gender gender,
                              String number,
                              String citizenship,
                              String birthplace,
                              String expirationDate,
                              String whoIssued, String issueDate) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.number = number;
        this.citizenship = citizenship;
        this.birthplace = birthplace;
        this.expirationDate = expirationDate;
        this.whoIssued = whoIssued;
        this.issueDate = issueDate;
    }
    
    public ForeignPassportDTO(String photo, String surname, String name, String patronymic, String birthdate,
            Gender gender, String number, String citizenship, String birthplace, String expirationDate,
            String whoIssued, String issueDate, String surnameLatin, String nameLatin, String citizenshipLatin,
            String birthplaceLatin) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.number = number;
        this.citizenship = citizenship;
        this.birthplace = birthplace;
        this.expirationDate = expirationDate;
        this.whoIssued = whoIssued;
        this.issueDate = issueDate;
        this.surnameLatin = surnameLatin;
        this.nameLatin = nameLatin;
        this.citizenshipLatin = citizenshipLatin;
        this.birthplaceLatin = birthplaceLatin;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getWhoIssued() {
        return whoIssued;
    }

    public void setWhoIssued(String whoIssued) {
        this.whoIssued = whoIssued;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getSurnameLatin() {
        return surnameLatin;
    }

    public void setSurnameLatin(String surnameLatin) {
        this.surnameLatin = surnameLatin;
    }

    public String getNameLatin() {
        return nameLatin;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
    }

    public String getCitizenshipLatin() {
        return citizenshipLatin;
    }

    public void setCitizenshipLatin(String citizenshipLatin) {
        this.citizenshipLatin = citizenshipLatin;
    }

    public String getBirthplaceLatin() {
        return birthplaceLatin;
    }

    public void setBirthplaceLatin(String birthplaceLatin) {
        this.birthplaceLatin = birthplaceLatin;
    }
    
}
