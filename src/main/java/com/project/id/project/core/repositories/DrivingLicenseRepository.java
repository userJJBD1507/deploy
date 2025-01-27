package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.DrivingLicense;

public interface DrivingLicenseRepository {

    void addDrivingLicense(DrivingLicense drivingLicense);

    DrivingLicense getDrivingLicense(Id id);

    void updateDrivingLicense(DrivingLicense drivingLicense);

    void deleteDrivingLicense(Id id);
}
