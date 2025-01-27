package com.project.id.project.application.services.linkers;

import com.project.id.project.core.Id;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Id> {
    Optional<Documents> findById(Id id);
    @Query("select doc from Documents doc where doc.voluntaryHealthInsurance.id=:id")
    public Documents getByVoluntaryHealthInsurance(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.taxPayerIdentificationNumber.id=:id")
    public Documents getByTaxPayerIdentificationNumber(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.birthCertificate.id=:id")
    public Documents getByBirthCertificate(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.compulsoryMedicalInsurance.id=:id")
    public Documents getByCompulsoryMedicalInsurance(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.foreignPassport.id=:id")
    public Documents getByForeignPassport(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.drivingLicense.id=:id")
    public Documents getByDrivingLicense(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.insuranceNumberOfIndividualPersonalAccount.id=:id")
    public Documents getByInsuranceNumberOfIndividualPersonalAccount(@Param("id") Id id);
    @Query("select doc from Documents doc where doc.passport.id=:id")
    public Documents getByPassport(@Param("id") Id id);
}
