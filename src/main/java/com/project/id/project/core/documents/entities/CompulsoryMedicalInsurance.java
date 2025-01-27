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
@Table(name = "required_health_insurance_table")
public class CompulsoryMedicalInsurance extends Document {
    @Column(name = "series_form_number")
    @Convert(converter = EncryptionConverter.class)
    private String seriesAndNumber;
    @Column(name = "series_and_form_number")
    @Convert(converter = EncryptionConverter.class)
    private String seriesAndFormNumber;
    @OneToOne(mappedBy = "compulsoryMedicalInsurance")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public CompulsoryMedicalInsurance() {
    }

    public CompulsoryMedicalInsurance(String seriesAndNumber, String seriesAndFormNumber) {
        this.seriesAndNumber = seriesAndNumber;
        this.seriesAndFormNumber = seriesAndFormNumber;
    }

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
    }

    public String getSeriesAndFormNumber() {
        return seriesAndFormNumber;
    }

    public void setSeriesAndFormNumber(String seriesAndFormNumber) {
        this.seriesAndFormNumber = seriesAndFormNumber;
    }

    @Override
    public String toString() {
        return "CompulsoryMedicalInsurance{" +
                "seriesAndNumber='" + seriesAndNumber + '\'' +
                ", seriesAndFormNumber='" + seriesAndFormNumber + '\'' +
                '}';
    }
}
