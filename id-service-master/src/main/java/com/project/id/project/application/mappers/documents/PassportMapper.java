package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.PassportDTO;
import com.project.id.project.core.documents.entities.Passport;

public class PassportMapper {
    public static PassportDTO toDTO(Passport passport) {
        PassportDTO passportDTO = new PassportDTO();
        passportDTO.setName(passport.getName());
        passportDTO.setGender(passport.getGender());
        passportDTO.setPhoto(passport.getPhoto());
        passportDTO.setSurname(passport.getSurname());
        passportDTO.setSeriesAndNumber(passport.getSeriesAndNumber());
        passportDTO.setPatronymic(passport.getPatronymic());
        passportDTO.setBirthdate(passport.getBirthdate());
        passportDTO.setBirthplace(passport.getBirthplace());
        passportDTO.setWhoIssued(passport.getWhoIssued());
        passportDTO.setDivisionCode(passport.getDivisionCode());
        passportDTO.setIssueDate(passport.getIssueDate());
        passportDTO.setRegistrationAddress(passport.getRegistrationAddress());
        return passportDTO;
    }
    public static Passport toEntity(PassportDTO passportDTO) {
        Passport passport = new Passport();
        passport.setName(passportDTO.getName());
        passport.setGender(passportDTO.getGender());
        passport.setPhoto(passportDTO.getPhoto());
        passport.setSurname(passportDTO.getSurname());
        passport.setSeriesAndNumber(passportDTO.getSeriesAndNumber());
        passport.setPatronymic(passportDTO.getPatronymic());
        passport.setBirthdate(passportDTO.getBirthdate());
        passport.setBirthplace(passportDTO.getBirthplace());
        passport.setWhoIssued(passportDTO.getWhoIssued());
        passport.setDivisionCode(passportDTO.getDivisionCode());
        passport.setIssueDate(passportDTO.getIssueDate());
        passport.setRegistrationAddress(passportDTO.getRegistrationAddress());
        return passport;
    }
}
