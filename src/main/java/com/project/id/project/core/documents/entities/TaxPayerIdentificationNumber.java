package com.project.id.project.core.documents.entities;

import com.project.id.project.application.services.enc.EncryptionConverter;
import com.project.id.project.core.Document;
import com.project.id.project.core.overall.Documents;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "taxpayer_identification_number_table")
public class TaxPayerIdentificationNumber extends Document {
    @Column(name = "taxpayer_identification_number")
    @Convert(converter = EncryptionConverter.class)
    private String taxpayerIdentificationNumber;
    @Column(name = "issue_date")
    @Convert(converter = EncryptionConverter.class)
    private String issueDate;
    @Column(name = "birthplace")
    @Convert(converter = EncryptionConverter.class)
    private String birthPlace;
    @Column(name = "effective_until")
    @Convert(converter = EncryptionConverter.class)
    private String effectiveUntil;
    @Column(name = "issuing_authority")
    @Convert(converter = EncryptionConverter.class)
    private String issuingAuthority;
    @OneToOne(mappedBy = "taxPayerIdentificationNumber")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public TaxPayerIdentificationNumber() {
    }

    public TaxPayerIdentificationNumber(String taxpayerIdentificationNumber,
                                        String issueDate,
                                        String birthPlace,
                                        String effectiveUntil,
                                        String issuingAuthority) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
        this.issueDate = issueDate;
        this.birthPlace = birthPlace;
        this.effectiveUntil = effectiveUntil;
        this.issuingAuthority = issuingAuthority;
    }

    public String getTaxpayerIdentificationNumber() {
        return taxpayerIdentificationNumber;
    }

    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getEffectiveUntil() {
        return effectiveUntil;
    }

    public void setEffectiveUntil(String effectiveUntil) {
        this.effectiveUntil = effectiveUntil;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    @Override
    public String toString() {
        return "TaxPayerIdentificationNumber{" +
                "taxpayerIdentificationNumber='" + taxpayerIdentificationNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", effectiveUntil='" + effectiveUntil + '\'' +
                ", issuingAuthority='" + issuingAuthority + '\'' +
                '}';
    }
}
