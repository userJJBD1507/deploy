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

import com.project.id.project.application.DTOs.emails.AdditionalEmailDTO;
import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.services.emails.AdditionalEmailService;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class AdditionalEmailServiceTest {

    @Mock
    private JpaAdditionalEmailRepository additionalEmailRepository;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private AdditionalEmailService additionalEmailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        String username = "testuser";
        AdditionalEmailDTO additionalEmailDTO = new AdditionalEmailDTO();
        PersonalData personalData = new PersonalData();
        AdditionalEmail entity = new AdditionalEmail();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));

        additionalEmailService.create(username, additionalEmailDTO);

        verify(entityPersonalDataRepository, times(1)).findByInvocation(username);
    }

    @Test
    public void testCreateThrowsExceptionWhenPersonalDataNotFound() {
        String username = "testuser";
        AdditionalEmailDTO additionalEmailDTO = new AdditionalEmailDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> additionalEmailService.create(username, additionalEmailDTO));

        verify(additionalEmailRepository, never()).addAdditionalEmail(any());
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        AdditionalEmail entity = new AdditionalEmail();

        when(additionalEmailRepository.getAdditionalEmail(id)).thenReturn(entity);

        Optional<AdditionalEmailDTO> result = additionalEmailService.read(id);

        assertTrue(result.isPresent());
        verify(additionalEmailRepository, times(1)).getAdditionalEmail(id);
    }

    @Test
    public void testReadReturnsEmptyWhenExceptionOccurs() {
        Id id = new Id();

        when(additionalEmailRepository.getAdditionalEmail(id)).thenThrow(new RuntimeException("Read failed"));

        Optional<AdditionalEmailDTO> result = additionalEmailService.read(id);

        assertTrue(result.isEmpty());
        verify(additionalEmailRepository, times(1)).getAdditionalEmail(id);
    }

    @Test
    public void testUpdateSuccess() {
        Id id = new Id();
        AdditionalEmailDTO additionalEmailDTO = new AdditionalEmailDTO();
        PersonalData personalData = new PersonalData();
        AdditionalEmail entity = new AdditionalEmail();

        when(additionalEmailRepository.getAdditionalEmail(id)).thenReturn(entity);
        when(entityPersonalDataRepository.findById(any(Id.class))).thenReturn(Optional.of(personalData));
        doNothing().when(additionalEmailRepository).updateAdditionalEmail(any(AdditionalEmail.class));

        additionalEmailService.update(id, additionalEmailDTO, new Id());

        verify(additionalEmailRepository, times(1)).updateAdditionalEmail(any(AdditionalEmail.class));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();

        doNothing().when(additionalEmailRepository).deleteAdditionalEmail(id);

        additionalEmailService.delete(id);

        verify(additionalEmailRepository, times(1)).deleteAdditionalEmail(id);
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        doThrow(new RuntimeException("Deletion failed")).when(additionalEmailRepository).deleteAdditionalEmail(id);

        assertDoesNotThrow(() -> additionalEmailService.delete(id));
    }
}
