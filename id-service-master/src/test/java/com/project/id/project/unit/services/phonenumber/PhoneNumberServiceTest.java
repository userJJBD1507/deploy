package com.project.id.project.unit.services.phonenumber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;


import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.application.DTOs.phonenumbers.PhoneNumberDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaPhoneNumberRepository;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;
import com.project.id.project.infrastructure.services.phonenumber.PhoneNumberService;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceTest {

    @Mock
    private JpaPhoneNumberRepository phoneNumberRepository;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        String username = "testuser";
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        PersonalData personalData = new PersonalData();
        PhoneNumber entity = new PhoneNumber();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        doNothing().when(phoneNumberRepository).addPhoneNumber(any(PhoneNumber.class));

        phoneNumberService.create(username, phoneNumberDTO);

        verify(entityPersonalDataRepository, times(1)).findByInvocation(username);
        verify(phoneNumberRepository, times(1)).addPhoneNumber(any(PhoneNumber.class));
        verify(entityPersonalDataRepository, times(1)).save(any(PersonalData.class));
    }

    @Test
    public void testCreateThrowsExceptionWhenPersonalDataNotFound() {
        String username = "testuser";
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> phoneNumberService.create(username, phoneNumberDTO));

        verify(phoneNumberRepository, never()).addPhoneNumber(any());
        verify(entityPersonalDataRepository, never()).save(any());
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        PhoneNumber entity = new PhoneNumber();

        when(phoneNumberRepository.getPhoneNumber(id)).thenReturn(entity);

        Optional<PhoneNumberDTO> result = phoneNumberService.read(id);

        assertTrue(result.isPresent());
        verify(phoneNumberRepository, times(1)).getPhoneNumber(id);
    }

    @Test
    public void testUpdateSuccess() {
        Id id = new Id();
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        PersonalData personalData = new PersonalData();
        PhoneNumber entity = new PhoneNumber();

        when(phoneNumberRepository.getPhoneNumber(id)).thenReturn(entity);
        when(entityPersonalDataRepository.findById(any(Id.class))).thenReturn(Optional.of(personalData));
        doNothing().when(phoneNumberRepository).updatePhoneNumber(any(PhoneNumber.class));

        phoneNumberService.update(id, phoneNumberDTO, new Id());

        verify(phoneNumberRepository, times(1)).updatePhoneNumber(any(PhoneNumber.class));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();

        doNothing().when(phoneNumberRepository).deletePhoneNumber(id);

        phoneNumberService.delete(id);

        verify(phoneNumberRepository, times(1)).deletePhoneNumber(id);
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        doThrow(new RuntimeException("Deletion failed")).when(phoneNumberRepository).deletePhoneNumber(id);

        assertDoesNotThrow(() -> phoneNumberService.delete(id));
    }
}
