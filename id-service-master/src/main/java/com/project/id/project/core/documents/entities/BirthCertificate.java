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
@Table(name = "birth_certificate_table")
public class BirthCertificate extends Document {
    @Column(name = "series_number")
    @Convert(converter = EncryptionConverter.class)
    private String seriesAndNumber;
    @Column(name = "citizenship")
    @Convert(converter = EncryptionConverter.class)
    private String citizenship;
    @Column(name = "birthplace")
    @Convert(converter = EncryptionConverter.class)
    private String birthplace;
    @Column(name = "birth_record_num")
    @Convert(converter = EncryptionConverter.class)
    private String birthRecordData;
    @Column(name = "fathers_surname")
    @Convert(converter = EncryptionConverter.class)
    private String fathersSurname;
    @Column(name = "fathers_name")
    @Convert(converter = EncryptionConverter.class)
    private String fathersName;
    @Column(name = "fathers_patronymic")
    @Convert(converter = EncryptionConverter.class)
    private String fathersPatronymic;
    @Column(name = "fathers_citizenship")
    @Convert(converter = EncryptionConverter.class)
    private String fathersCitizenship;
    @Column(name = "fathers_birthdate")
    @Convert(converter = EncryptionConverter.class)
    private String fathersBirthdate;
    @Column(name = "mothers_surname")
    @Convert(converter = EncryptionConverter.class)
    private String mothersSurname;
    @Column(name = "mothers_name")
    @Convert(converter = EncryptionConverter.class)
    private String mothersName;
    @Column(name = "mothers_patronymic")
    @Convert(converter = EncryptionConverter.class)
    private String mothersPatronymic;
    @Column(name = "mothers_citizenship")
    @Convert(converter = EncryptionConverter.class)
    private String mothersCitizenship;
    @Column(name = "mothers_birthdate")
    @Convert(converter = EncryptionConverter.class)
    private String mothersBirthdate;
    @Column(name = "registration_place")
    @Convert(converter = EncryptionConverter.class)
    private String stateRegistrationPlace;
    @Column(name = "place_issue_certificate")
    @Convert(converter = EncryptionConverter.class)
    private String issueCertificatePlace;
    @Column(name = "issue_date")
    @Convert(converter = EncryptionConverter.class)
    private String issueString;
    @OneToOne(mappedBy = "birthCertificate")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public BirthCertificate() {
    }

    public BirthCertificate(String seriesAndNumber,
                            String citizenship,
                            String birthplace,
                            String birthRecordData,
                            String fathersSurname,
                            String fathersName,
                            String fathersPatronymic,
                            String fathersCitizenship,
                            String fathersBirthdate,
                            String mothersSurname,
                            String mothersName,
                            String mothersPatronymic,
                            String mothersCitizenship,
                            String mothersBirthdate,
                            String stateRegistrationPlace,
                            String issueCertificatePlace,
                            String issueString) {
        this.seriesAndNumber = seriesAndNumber;
        this.citizenship = citizenship;
        this.birthplace = birthplace;
        this.birthRecordData = birthRecordData;
        this.fathersSurname = fathersSurname;
        this.fathersName = fathersName;
        this.fathersPatronymic = fathersPatronymic;
        this.fathersCitizenship = fathersCitizenship;
        this.fathersBirthdate = fathersBirthdate;
        this.mothersSurname = mothersSurname;
        this.mothersName = mothersName;
        this.mothersPatronymic = mothersPatronymic;
        this.mothersCitizenship = mothersCitizenship;
        this.mothersBirthdate = mothersBirthdate;
        this.stateRegistrationPlace = stateRegistrationPlace;
        this.issueCertificatePlace = issueCertificatePlace;
        this.issueString = issueString;
    }

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
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

    public String getBirthRecordData() {
        return birthRecordData;
    }

    public void setBirthRecordData(String birthRecordData) {
        this.birthRecordData = birthRecordData;
    }

    public String getFathersSurname() {
        return fathersSurname;
    }

    public void setFathersSurname(String fathersSurname) {
        this.fathersSurname = fathersSurname;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getFathersPatronymic() {
        return fathersPatronymic;
    }

    public void setFathersPatronymic(String fathersPatronymic) {
        this.fathersPatronymic = fathersPatronymic;
    }

    public String getFathersCitizenship() {
        return fathersCitizenship;
    }

    public void setFathersCitizenship(String fathersCitizenship) {
        this.fathersCitizenship = fathersCitizenship;
    }

    public String getFathersBirthdate() {
        return fathersBirthdate;
    }

    public void setFathersBirthdate(String fathersBirthdate) {
        this.fathersBirthdate = fathersBirthdate;
    }

    public String getMothersSurname() {
        return mothersSurname;
    }

    public void setMothersSurname(String mothersSurname) {
        this.mothersSurname = mothersSurname;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getMothersPatronymic() {
        return mothersPatronymic;
    }

    public void setMothersPatronymic(String mothersPatronymic) {
        this.mothersPatronymic = mothersPatronymic;
    }

    public String getMothersCitizenship() {
        return mothersCitizenship;
    }

    public void setMothersCitizenship(String mothersCitizenship) {
        this.mothersCitizenship = mothersCitizenship;
    }

    public String getMothersBirthdate() {
        return mothersBirthdate;
    }

    public void setMothersBirthdate(String mothersBirthdate) {
        this.mothersBirthdate = mothersBirthdate;
    }

    public String getStateRegistrationPlace() {
        return stateRegistrationPlace;
    }

    public void setStateRegistrationPlace(String stateRegistrationPlace) {
        this.stateRegistrationPlace = stateRegistrationPlace;
    }

    public String getIssueCertificatePlace() {
        return issueCertificatePlace;
    }

    public void setIssueCertificatePlace(String issueCertificatePlace) {
        this.issueCertificatePlace = issueCertificatePlace;
    }

    public String getIssueString() {
        return issueString;
    }

    public void setIssueString(String issueString) {
        this.issueString = issueString;
    }

    @Override
    public String toString() {
        return "BirthCertificate{" +
                "seriesAndNumber='" + seriesAndNumber + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", birthRecordData='" + birthRecordData + '\'' +
                ", fathersSurname='" + fathersSurname + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", fathersPatronymic='" + fathersPatronymic + '\'' +
                ", fathersCitizenship='" + fathersCitizenship + '\'' +
                ", fathersBirthdate='" + fathersBirthdate + '\'' +
                ", mothersSurname='" + mothersSurname + '\'' +
                ", mothersName='" + mothersName + '\'' +
                ", mothersPatronymic='" + mothersPatronymic + '\'' +
                ", mothersCitizenship='" + mothersCitizenship + '\'' +
                ", mothersBirthdate='" + mothersBirthdate + '\'' +
                ", stateRegistrationPlace='" + stateRegistrationPlace + '\'' +
                ", issueCertificatePlace='" + issueCertificatePlace + '\'' +
                ", issueString='" + issueString + '\'' +
                '}';
    }
}
