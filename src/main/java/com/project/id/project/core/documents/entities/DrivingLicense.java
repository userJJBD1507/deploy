package com.project.id.project.core.documents.entities;

import com.project.id.project.application.services.enc.EncryptionConverter;
import com.project.id.project.core.Document;
import com.project.id.project.core.Id;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.utils.DrivingCategory;
import com.project.id.project.core.utils.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "driving_license_table")
public class DrivingLicense extends Document {
    @Column(name = "number")
    @Convert(converter = EncryptionConverter.class)
    private String number;
    @Column(name = "birthplace")
    @Convert(converter = EncryptionConverter.class)
    private String birthplace;
    @Column(name = "whom_issued")
    @Convert(converter = EncryptionConverter.class)
    private String whoIssued;
    @Column(name = "validity_until")
    @Convert(converter = EncryptionConverter.class)
    private String validUntil;
    @Column(name = "issue_date")
    @Convert(converter = EncryptionConverter.class)
    private String issueDate;
    @Column(name = "issue_place")
    @Convert(converter = EncryptionConverter.class)
    private String placeOfIssue;
    @Column(name = "special_marks")
    @Convert(converter = EncryptionConverter.class)
    private String comments;
    @Column(name = "category")
    private DrivingCategory category;

    @Convert(converter = EncryptionConverter.class)
    @Column(name = "surname_latin")
    private String surnameLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "name_latin")
    private String nameLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "patronymic_latin")
    private String patronymicLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "birthplace_latin")
    private String birthplaceLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "who_issued_latin")
    private String whoIssuedLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "place_of_issue_latin")
    private String placeOfIssueLatin;

    @OneToOne(mappedBy = "drivingLicense")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public DrivingLicense() {
    }

    public DrivingLicense(String number,
                          String birthplace,
                          String whoIssued,
                          String validUntil,
                          String issueDate,
                          String placeOfIssue,
                          String comments,
                          DrivingCategory category) {
        this.number = number;
        this.birthplace = birthplace;
        this.whoIssued = whoIssued;
        this.validUntil = validUntil;
        this.issueDate = issueDate;
        this.placeOfIssue = placeOfIssue;
        this.comments = comments;
        this.category = category;
    }
    
    public DrivingLicense(Id id, String photo, String surname, String name, String patronymic, String birthdate,
            Gender gender, String number, String birthplace, String whoIssued, String validUntil, String issueDate,
            String placeOfIssue, String comments, DrivingCategory category, String surnameLatin, String nameLatin,
            String patronymicLatin, String birthplaceLatin, String whoIssuedLatin, String placeOfIssueLatin) {
        super(id, photo, surname, name, patronymic, birthdate, gender);
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

    @Override
    public String toString() {
        return "DrivingLicense{" +
                "number='" + number + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", whoIssued='" + whoIssued + '\'' +
                ", validUntil='" + validUntil + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", placeOfIssue='" + placeOfIssue + '\'' +
                ", comments='" + comments + '\'' +
                ", category=" + category +
                '}';
    }
}
