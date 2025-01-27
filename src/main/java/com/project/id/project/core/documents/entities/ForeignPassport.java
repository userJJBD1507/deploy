package com.project.id.project.core.documents.entities;

import com.project.id.project.application.services.enc.EncryptionConverter;
import com.project.id.project.core.Document;
import com.project.id.project.core.Id;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.utils.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Table(name = "foreign_passport_table")
@Entity
public class ForeignPassport extends Document {
    @Column(name = "number")
    @Convert(converter = EncryptionConverter.class)
    private String number;
    @Column(name = "citizenship")
    @Convert(converter = EncryptionConverter.class)
    private String citizenship;
    @Column(name = "birthplace")
    @Convert(converter = EncryptionConverter.class)
    private String birthplace;
    @Column(name = "expiration_date")
    @Convert(converter = EncryptionConverter.class)
    private String expirationDate;
    @Column(name = "who_issued")
    @Convert(converter = EncryptionConverter.class)
    private String whoIssued;
    @Column(name = "issue_date")
    @Convert(converter = EncryptionConverter.class)
    private String issueDate;
    @OneToOne(mappedBy = "foreignPassport")
    private Documents documents;
    @Version
    private Long version;

    @Convert(converter = EncryptionConverter.class)
    @Column(name = "surname_latin")
    private String surnameLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "name_latin")
    private String nameLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "citizenship_latin")
    private String citizenshipLatin;
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "birthplace_latin")
    private String birthplaceLatin;


    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public ForeignPassport() {
    }

    public ForeignPassport(String number,
                           String citizenship,
                           String birthplace,
                           String expirationDate,
                           String whoIssued, String issueDate) {
        this.number = number;
        this.citizenship = citizenship;
        this.birthplace = birthplace;
        this.expirationDate = expirationDate;
        this.whoIssued = whoIssued;
        this.issueDate = issueDate;
    }
    
    public ForeignPassport(Id id, String photo, String surname, String name, String patronymic, String birthdate,
            Gender gender, String number, String citizenship, String birthplace, String expirationDate,
            String whoIssued, String issueDate, String surnameLatin, String nameLatin, String citizenshipLatin,
            String birthplaceLatin) {
        super(id, photo, surname, name, patronymic, birthdate, gender);
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

    @Override
    public String toString() {
        return "ForeignPassport{" +
                "number='" + number + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", whoIssued='" + whoIssued + '\'' +
                ", issueDate='" + issueDate + '\'' +
                '}';
    }
}
