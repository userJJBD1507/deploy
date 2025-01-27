package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;

public interface AdditionalPhoneNumberRepository {

    void addAdditionalPhoneNumber(AdditionalPhoneNumber additionalPhoneNumber);

    AdditionalPhoneNumber getAdditionalPhoneNumber(Id id);

    void updateAdditionalPhoneNumber(AdditionalPhoneNumber additionalPhoneNumber);

    void deleteAdditionalPhoneNumber(Id id);
}
