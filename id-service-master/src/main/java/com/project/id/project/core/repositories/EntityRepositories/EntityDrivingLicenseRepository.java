package com.project.id.project.core.repositories.EntityRepositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.documents.entities.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EntityDrivingLicenseRepository extends JpaRepository<DrivingLicense, Id> {
    @Modifying
    @Transactional
    @Query("delete DrivingLicense w where w.id=:id")
    void delete(@Param("id") Id id);
}
