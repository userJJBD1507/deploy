package com.project.id.project.unit.services.personal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.project.id.project.application.DTOs.personal.PersonalDataDTO;
import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.core.Id;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalDataRepository;
import com.project.id.project.infrastructure.services.personal.PersonalDataService;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PersonalDataServiceTest {

    @Mock
    private JpaPersonalDataRepository jpaPersonalDataRepository;

    @Mock
    private AddressesRepository addressesRepository;

    @Mock
    private DocumentsRepository documentsRepository;

    @InjectMocks
    private PersonalDataService personalDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        PersonalDataDTO personalDataDTO = new PersonalDataDTO();
        PersonalData personalData = new PersonalData();

        when(addressesRepository.save(any(Addresses.class))).thenReturn(new Addresses());
        when(documentsRepository.save(any(Documents.class))).thenReturn(new Documents());

        personalDataService.create(personalDataDTO);

        verify(jpaPersonalDataRepository, times(1)).addPersonalData(any(PersonalData.class));
        verify(addressesRepository, times(1)).save(any(Addresses.class));
        verify(documentsRepository, times(1)).save(any(Documents.class));
    }

    @Test
    public void testCreateThrowsException() {
        PersonalDataDTO personalDataDTO = new PersonalDataDTO();


        assertDoesNotThrow(() -> personalDataService.create(personalDataDTO));

        verify(jpaPersonalDataRepository, times(1)).addPersonalData(any(PersonalData.class));
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        PersonalData personalData = new PersonalData();

        when(jpaPersonalDataRepository.getPersonalData(id)).thenReturn(personalData);

        Optional<PersonalDataDTO> result = personalDataService.read(id);

        assertTrue(result.isPresent());
        verify(jpaPersonalDataRepository, times(1)).getPersonalData(id);
    }

    @Test
    public void testReadReturnsEmptyWhenExceptionOccurs() {
        Id id = new Id();

        when(jpaPersonalDataRepository.getPersonalData(id)).thenThrow(new RuntimeException("Read failed"));

        Optional<PersonalDataDTO> result = personalDataService.read(id);

        assertTrue(result.isEmpty());
        verify(jpaPersonalDataRepository, times(1)).getPersonalData(id);
    }

    @Test
    public void testUpdateSuccess() {
        Id id = new Id();
        PersonalDataDTO personalDataDTO = new PersonalDataDTO();
        PersonalData personalData = new PersonalData();


        personalDataService.update(id, personalDataDTO);

        verify(jpaPersonalDataRepository, times(1)).addPersonalData(any(PersonalData.class));
    }

    @Test
    public void testUpdateThrowsException() {
        Id id = new Id();
        PersonalDataDTO personalDataDTO = new PersonalDataDTO();

        assertDoesNotThrow(() -> personalDataService.update(id, personalDataDTO));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();

        doNothing().when(jpaPersonalDataRepository).deletePersonalData(id);

        personalDataService.delete(id);

        verify(jpaPersonalDataRepository, times(1)).deletePersonalData(id);
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        doThrow(new RuntimeException("Deletion failed")).when(jpaPersonalDataRepository).deletePersonalData(id);

        assertDoesNotThrow(() -> personalDataService.delete(id));
    }
}
