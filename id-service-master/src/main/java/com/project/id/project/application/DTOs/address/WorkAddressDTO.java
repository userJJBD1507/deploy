package com.project.id.project.application.DTOs.address;

import java.util.Objects;

import com.project.id.project.core.Id;

import io.swagger.v3.oas.annotations.media.Schema;

public class WorkAddressDTO {
    @Schema(description = "имя рабочего адреса")
    private String name;
    @Schema(description = "название города")
    private String city;
    @Schema(description = "название региона")
    private String region;
    @Schema(description = "название улицы")
    private String street;
    @Schema(description = "номер дома")
    private int house;
    @Schema(description = "номер подьезда")
    private int subway;
    @Schema(description = "номер этажа")
    private int floor;
    @Schema(description = "номер апартаментов")
    private int apartment;
    @Schema(description = "номер подьезда")
    private int intercom;

    public WorkAddressDTO() {
    }

    public WorkAddressDTO(String name, String city, String region, String street, int house, int subway, int floor, int apartment, int intercom) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkAddressDTO that = (WorkAddressDTO) o;
        return house == that.house &&
               subway == that.subway &&
               floor == that.floor &&
               apartment == that.apartment &&
               intercom == that.intercom &&
               Objects.equals(name, that.name) &&
               Objects.equals(city, that.city) &&
               Objects.equals(region, that.region) &&
               Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, region, street, house, subway, floor, apartment, intercom);
    }
}
