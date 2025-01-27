package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.WorkAddress;

public interface WorkAddressRepository {

    void addWorkAddress(WorkAddress workAddress);

    WorkAddress getWorkAddress(Id id);

    void updateWorkAddress(WorkAddress workAddress);

    void deleteWorkAddress(Id id);
}
