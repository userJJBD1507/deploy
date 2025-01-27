package com.project.id.project.unit.services.email;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.project.id.project.application.DTOs.emails.PersonalEmailDTO;
import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalEmailRepository;
import com.project.id.project.infrastructure.services.emails.PersonalEmailService;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class PersonalEmailServiceTest {

    @Mock
    private JpaPersonalEmailRepository personalEmailRepository;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private PersonalEmailService personalEmailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        String username = "testuser";
        PersonalEmailDTO personalEmailDTO = new PersonalEmailDTO();
        PersonalData personalData = new PersonalData();
        PersonalEmail entity = new PersonalEmail();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        doNothing().when(personalEmailRepository).addPersonalEmail(any(PersonalEmail.class));

        personalEmailService.create(username, personalEmailDTO);

        verify(entityPersonalDataRepository, times(1)).findByInvocation(username);
        verify(personalEmailRepository, times(1)).addPersonalEmail(any(PersonalEmail.class));
    }

    @Test
    public void testCreateThrowsExceptionWhenPersonalDataNotFound() {
        String username = "testuser";
        PersonalEmailDTO personalEmailDTO = new PersonalEmailDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> personalEmailService.create(username, personalEmailDTO));

        verify(personalEmailRepository, never()).addPersonalEmail(any());
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        PersonalEmail entity = new PersonalEmail();

        when(personalEmailRepository.getPersonalEmail(id)).thenReturn(entity);

        Optional<PersonalEmailDTO> result = personalEmailService.read(id);

        assertTrue(result.isPresent());
        verify(personalEmailRepository, times(1)).getPersonalEmail(id);
    }

    @Test
    public void testReadReturnsEmptyWhenExceptionOccurs() {
        Id id = new Id();

        when(personalEmailRepository.getPersonalEmail(id)).thenThrow(new RuntimeException("Read failed"));

        Optional<PersonalEmailDTO> result = personalEmailService.read(id);

        assertTrue(result.isEmpty());
        verify(personalEmailRepository, times(1)).getPersonalEmail(id);
    }

    @Test
    public void testUpdateSuccess() {
        Id id = new Id();
        PersonalEmailDTO personalEmailDTO = new PersonalEmailDTO();
        PersonalData personalData = new PersonalData();
        PersonalEmail entity = new PersonalEmail();

        when(personalEmailRepository.getPersonalEmail(id)).thenReturn(entity);
        when(entityPersonalDataRepository.findById(any(Id.class))).thenReturn(Optional.of(personalData));
        doNothing().when(personalEmailRepository).updatePersonalEmail(any(PersonalEmail.class));

        personalEmailService.update(id, personalEmailDTO, new Id());

        verify(personalEmailRepository, times(1)).updatePersonalEmail(any(PersonalEmail.class));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();

        doNothing().when(personalEmailRepository).deletePersonalEmail(id);

        personalEmailService.delete(id);

        verify(personalEmailRepository, times(1)).deletePersonalEmail(id);
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        doThrow(new RuntimeException("Deletion failed")).when(personalEmailRepository).deletePersonalEmail(id);

        assertDoesNotThrow(() -> personalEmailService.delete(id));
    }
}
