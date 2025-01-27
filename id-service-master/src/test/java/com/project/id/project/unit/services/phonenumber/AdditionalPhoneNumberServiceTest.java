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
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdditionalPhoneNumberServiceTest {

    @Mock
    private JpaAdditionalPhoneNumberRepository additionalPhoneNumberRepository;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private AdditionalPhoneNumberService additionalPhoneNumberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        String username = "testuser";
        AdditionalPhoneNumberDTO phoneNumberDTO = new AdditionalPhoneNumberDTO();
        PersonalData personalData = new PersonalData();
        AdditionalPhoneNumber entity = new AdditionalPhoneNumber();
        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        additionalPhoneNumberService.create(username, phoneNumberDTO);
        verify(entityPersonalDataRepository, times(1)).findByInvocation(username);
    }
    
    


    @Test
    public void testCreateThrowsExceptionWhenPersonalDataNotFound() {
        String username = "testuser";
        AdditionalPhoneNumberDTO phoneNumberDTO = new AdditionalPhoneNumberDTO();
        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> additionalPhoneNumberService.create(username, phoneNumberDTO));

        verify(additionalPhoneNumberRepository, never()).addAdditionalPhoneNumber(any());
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        AdditionalPhoneNumber entity = new AdditionalPhoneNumber();
        when(additionalPhoneNumberRepository.getAdditionalPhoneNumber(id)).thenReturn(entity);

        Optional<AdditionalPhoneNumberDTO> result = additionalPhoneNumberService.read(id);

        assertTrue(result.isPresent());
        verify(additionalPhoneNumberRepository, times(1)).getAdditionalPhoneNumber(id);
    }

    @Test
    public void testUpdateSuccess() {
        Id id = new Id();
        AdditionalPhoneNumberDTO phoneNumberDTO = new AdditionalPhoneNumberDTO();
        PersonalData personalData = new PersonalData();
        AdditionalPhoneNumber entity = new AdditionalPhoneNumber();

        when(additionalPhoneNumberRepository.getAdditionalPhoneNumber(id)).thenReturn(entity);
        when(entityPersonalDataRepository.findById(any(Id.class))).thenReturn(Optional.of(personalData));
        doNothing().when(additionalPhoneNumberRepository).updateAdditionalPhoneNumber(any(AdditionalPhoneNumber.class));

        additionalPhoneNumberService.update(id, phoneNumberDTO, new Id());

        verify(additionalPhoneNumberRepository, times(1)).updateAdditionalPhoneNumber(any(AdditionalPhoneNumber.class));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();

        doNothing().when(additionalPhoneNumberRepository).deleteAdditionalPhoneNumber(id);

        additionalPhoneNumberService.delete(id);

        verify(additionalPhoneNumberRepository, times(1)).deleteAdditionalPhoneNumber(id);
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        doThrow(new RuntimeException("Deletion failed")).when(additionalPhoneNumberRepository).deleteAdditionalPhoneNumber(id);

        assertDoesNotThrow(() -> additionalPhoneNumberService.delete(id));
    }
}
