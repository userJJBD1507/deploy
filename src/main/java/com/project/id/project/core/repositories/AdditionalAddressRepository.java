package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;

public interface AdditionalAddressRepository {
    public void addAdditionalAddress(AdditionalAddress additionalAddress);
    public AdditionalAddress getAdditionalAddress(Id id);
    public void updateAdditionalAddress(AdditionalAddress additionalAddress);
    public void deleteAdditionalAddress(Id id);
}
