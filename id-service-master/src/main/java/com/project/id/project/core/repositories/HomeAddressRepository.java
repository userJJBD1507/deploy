package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.HomeAddress;

public interface HomeAddressRepository {

    void addHomeAddress(HomeAddress homeAddress);

    HomeAddress getHomeAddress(Id id);

    void updateHomeAddress(HomeAddress homeAddress);

    void deleteHomeAddress(Id id);
}
