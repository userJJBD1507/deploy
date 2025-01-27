package com.project.id.project.application.services.linkers;

import com.project.id.project.core.Id;
import com.project.id.project.core.overall.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressesRepository extends JpaRepository<Addresses, Id> {
    Optional<Addresses> findById(Id id);
}
