package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.phones.entities.PhoneNumber;

public interface PhoneNumberRepository {

    void addPhoneNumber(PhoneNumber phoneNumber);

    PhoneNumber getPhoneNumber(Id id);

    void updatePhoneNumber(PhoneNumber phoneNumber);

    void deletePhoneNumber(Id id);
}
