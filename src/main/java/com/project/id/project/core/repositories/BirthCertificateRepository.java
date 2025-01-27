package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.BirthCertificate;

public interface BirthCertificateRepository {

    void addBirthCertificate(BirthCertificate birthCertificate);

    BirthCertificate getBirthCertificate(Id id);

    void updateBirthCertificate(BirthCertificate birthCertificate);

    void deleteBirthCertificate(Id id);
}
