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

@Entity
@Table(name = "voluntary_health_insurance_table")
public class VoluntaryHealthInsurance extends Document {
    @Column(name = "onset_of_action")
    @Convert(converter = EncryptionConverter.class)
    private String effectiveFrom;
    @Column(name = "number")
    @Convert(converter = EncryptionConverter.class)
    private String number;
    @Column(name = "end_of_action")
    @Convert(converter = EncryptionConverter.class)
    private String effectiveUntil;
    @Column(name = "policyHolder")
    @Convert(converter = EncryptionConverter.class)
    private String policyHolder;
    @OneToOne(mappedBy = "voluntaryHealthInsurance")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public VoluntaryHealthInsurance() {

    }

    public VoluntaryHealthInsurance(Id id,
                                    String photo,
                                    String surname,
                                    String name,
                                    String patronymic,
                                    String birthdate,
                                    Gender gender,
                                    String effectiveFrom,
                                    String number,
                                    String effectiveUntil,
                                    String policyHolder, Documents documents) {
        super(id, photo, surname, name, patronymic, birthdate, gender);
        this.effectiveFrom = effectiveFrom;
        this.number = number;
        this.effectiveUntil = effectiveUntil;
        this.policyHolder = policyHolder;
        this.documents = documents;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveUntil() {
        return effectiveUntil;
    }

    public void setEffectiveUntil(String effectiveUntil) {
        this.effectiveUntil = effectiveUntil;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    @Override
    public String toString() {
        return "VoluntaryHealthInsurance{" +
                "effectiveFrom='" + effectiveFrom + '\'' +
                ", number='" + number + '\'' +
                ", effectiveUntil='" + effectiveUntil + '\'' +
                ", policyHolder='" + policyHolder + '\'' +
                '}';
    }
}
