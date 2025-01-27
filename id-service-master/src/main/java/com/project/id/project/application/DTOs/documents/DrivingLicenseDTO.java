package com.project.id.project.application.DTOs.documents;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.DrivingCategory;

public class DrivingLicenseDTO {
    private String photo;
    private String surname;
    private String name;
    private String patronymic;
    private String birthdate;
    private String number;
    private String birthplace;
    private String whoIssued;
    private String validUntil;
    private String issueDate;
    private String placeOfIssue;
    private String comments;
    private DrivingCategory category;

    private String surnameLatin;
    private String nameLatin;
    private String patronymicLatin;
    private String birthplaceLatin;
    private String whoIssuedLatin;
    private String placeOfIssueLatin;

    public DrivingLicenseDTO() {
    }

    public DrivingLicenseDTO(String photo,
                             String surname, String name,
                             String patronymic, String birthdate, String number,
                             String birthplace, String whoIssued, String validUntil,
                             String issueDate, String placeOfIssue, String comments,
                             DrivingCategory category) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.number = number;
        this.birthplace = birthplace;
        this.whoIssued = whoIssued;
        this.validUntil = validUntil;
        this.issueDate = issueDate;
        this.placeOfIssue = placeOfIssue;
        this.comments = comments;
        this.category = category;
    }
    
    public DrivingLicenseDTO(String photo, String surname, String name, String patronymic, String birthdate,
            String number, String birthplace, String whoIssued, String validUntil, String issueDate,
            String placeOfIssue, String comments, DrivingCategory category, String surnameLatin, String nameLatin,
            String patronymicLatin, String birthplaceLatin, String whoIssuedLatin, String placeOfIssueLatin) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.number = number;
        this.birthplace = birthplace;
        this.whoIssued = whoIssued;
        this.validUntil = validUntil;
        this.issueDate = issueDate;
        this.placeOfIssue = placeOfIssue;
        this.comments = comments;
        this.category = category;
        this.surnameLatin = surnameLatin;
        this.nameLatin = nameLatin;
        this.patronymicLatin = patronymicLatin;
        this.birthplaceLatin = birthplaceLatin;
        this.whoIssuedLatin = whoIssuedLatin;
        this.placeOfIssueLatin = placeOfIssueLatin;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getWhoIssued() {
        return whoIssued;
    }

    public void setWhoIssued(String whoIssued) {
        this.whoIssued = whoIssued;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public DrivingCategory getCategory() {
        return category;
    }

    public void setCategory(DrivingCategory category) {
        this.category = category;
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

    public String getPatronymicLatin() {
        return patronymicLatin;
    }

    public void setPatronymicLatin(String patronymicLatin) {
        this.patronymicLatin = patronymicLatin;
    }

    public String getBirthplaceLatin() {
        return birthplaceLatin;
    }

    public void setBirthplaceLatin(String birthplaceLatin) {
        this.birthplaceLatin = birthplaceLatin;
    }

    public String getWhoIssuedLatin() {
        return whoIssuedLatin;
    }

    public void setWhoIssuedLatin(String whoIssuedLatin) {
        this.whoIssuedLatin = whoIssuedLatin;
    }

    public String getPlaceOfIssueLatin() {
        return placeOfIssueLatin;
    }

    public void setPlaceOfIssueLatin(String placeOfIssueLatin) {
        this.placeOfIssueLatin = placeOfIssueLatin;
    }
    
}
