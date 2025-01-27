package com.project.id.project.core;

import com.project.id.project.application.services.enc.EncryptionConverter;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Address {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @Column(name = "name")
    @Convert(converter = EncryptionConverter.class)
    private String name;
    @Column(name = "city")
    @Convert(converter = EncryptionConverter.class)
    private String city;
    @Column(name = "region")
    @Convert(converter = EncryptionConverter.class)
    private String region;
    @Column(name = "street")
    @Convert(converter = EncryptionConverter.class)
    private String street;
    @Column(name = "house")
    private int house;
    @Column(name = "subway")
    private int subway;
    @Column(name = "floor")
    private int floor;
    @Column(name = "apartment")
    private int apartment;
    @Column(name = "intercom")
    private int intercom;
    public Address() {
        this.id = new Id();
    }
    public Address(Id id,
                   String name,
                   String city,
                   String region,
                   String street,
                   int house,
                   int subway,
                   int floor,
                   int apartment,
                   int intercom) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.street = street;
        this.house = house;
        this.subway = subway;
        this.floor = floor;
        this.apartment = apartment;
        this.intercom = intercom;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getSubway() {
        return subway;
    }

    public void setSubway(int subway) {
        this.subway = subway;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    public int getIntercom() {
        return intercom;
    }

    public void setIntercom(int intercom) {
        this.intercom = intercom;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", street='" + street + '\'' +
                ", house=" + house +
                ", subway=" + subway +
                ", floor=" + floor +
                ", apartment=" + apartment +
                ", intercom=" + intercom +
                '}';
    }
}
