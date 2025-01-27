package com.project.id.project.core.documents.entities;

import com.project.id.project.application.services.enc.EncryptionConverter;
import com.project.id.project.core.Document;
import com.project.id.project.core.overall.Documents;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "passport_table")
public class Passport extends Document {
    @Column(name = "series_number")
    @Convert(converter = EncryptionConverter.class)
    private String seriesAndNumber;
    @Column(name = "place_of_birth")
    @Convert(converter = EncryptionConverter.class)
    private String birthplace;
    @Column(name = "who_issued")
    @Convert(converter = EncryptionConverter.class)
    private String whoIssued;
    @Column(name = "division_code")
    @Convert(converter = EncryptionConverter.class)
    private String divisionCode;
    @Column(name = "issue_date")
    @Convert(converter = EncryptionConverter.class)
    private String issueDate;
    @Column(name = "registration_address")
    @Convert(converter = EncryptionConverter.class)
    private String registrationAddress;
    @OneToOne(mappedBy = "passport")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public Passport() {
    }

    public Passport(String seriesAndNumber,
                    String birthplace, String whoIssued,
                    String divisionCode,
                    String issueDate, String registrationAddress) {
        this.seriesAndNumber = seriesAndNumber;
        this.birthplace = birthplace;
        this.whoIssued = whoIssued;
        this.divisionCode = divisionCode;
        this.issueDate = issueDate;
        this.registrationAddress = registrationAddress;
    }

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
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

    @Override
    public String toString() {
        return "Passport{" +
                "seriesAndNumber='" + seriesAndNumber + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", whoIssued='" + whoIssued + '\'' +
                ", divisionCode='" + divisionCode + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", registrationAddress='" + registrationAddress + '\'' +
                '}';
    }
}
