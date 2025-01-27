package com.project.id.project.core.addresses.entities;

import com.project.id.project.core.Address;
import com.project.id.project.core.Id;
import com.project.id.project.core.overall.Addresses;
import jakarta.persistence.*;


@Entity
@Table(name = "work_address_table")
public class WorkAddress extends Address{
    public WorkAddress() {
    }

    public WorkAddress(Id id,
                       String name,
                       String city,
                       String region,
                       String street,
                       int house,
                       int subway,
                       int floor,
                       int apartment,
                       int intercom) {
        super(id, name, city, region, street, house, subway, floor, apartment, intercom);
    }
    @OneToOne
    @JoinColumn(name = "addresses_id")
    private Addresses addresses;
    @Version
    private Long version;
    public Addresses getAddresses() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "WorkAddress{" +
                "addresses=" + addresses +
                '}';
    }
}