package com.project.id.project.application.mappers.addresses;

import com.project.id.project.application.DTOs.address.WorkAddressDTO;
import com.project.id.project.core.addresses.entities.WorkAddress;

public class WorkAddressMapper {

    public static WorkAddressDTO toDTO(WorkAddress address) {
        WorkAddressDTO dto = new WorkAddressDTO();
        dto.setName(address.getName());
        dto.setCity(address.getCity());
        dto.setRegion(address.getRegion());
        dto.setStreet(address.getStreet());
        dto.setHouse(address.getHouse());
        dto.setSubway(address.getSubway());
        dto.setFloor(address.getFloor());
        dto.setApartment(address.getApartment());
        dto.setIntercom(address.getIntercom());
        return dto;
    }

    public static WorkAddress toEntity(WorkAddressDTO dto) {
        WorkAddress entity = new WorkAddress();
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setRegion(dto.getRegion());
        entity.setStreet(dto.getStreet());
        entity.setHouse(dto.getHouse());
        entity.setSubway(dto.getSubway());
        entity.setFloor(dto.getFloor());
        entity.setApartment(dto.getApartment());
        entity.setIntercom(dto.getIntercom());
        System.out.println(entity);
        return entity;
    }
}
