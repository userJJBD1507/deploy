package com.project.id.project.core.overall;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.personal.PersonalData;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "addresses_table")
public class Addresses {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @OneToMany(mappedBy = "addresses")
    private List<AdditionalAddress> additionalAddress;
    @OneToOne(mappedBy = "addresses")
    private HomeAddress homeAddress;
    @OneToOne(mappedBy = "addresses")
    private WorkAddress workAddress;
    @OneToOne
    @JoinColumn(name = "personal_data_id",  referencedColumnName = "id")
    private PersonalData personalData;

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Addresses() {
        this.id = new Id();
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public List<AdditionalAddress> getAdditionalAddress() {
        return additionalAddress;
    }

    public void setAdditionalAddress(List<AdditionalAddress> additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public HomeAddress getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(HomeAddress homeAddress) {
        this.homeAddress = homeAddress;
    }

    public WorkAddress getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(WorkAddress workAddress) {
        this.workAddress = workAddress;
    }


}
