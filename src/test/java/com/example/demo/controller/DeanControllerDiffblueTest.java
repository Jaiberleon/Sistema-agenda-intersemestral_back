package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.FacultyEntity;
import com.example.demo.model.ProgramEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DeanService;
import com.example.demo.service.imp.DeanServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {DeanController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DeanControllerDiffblueTest {
    @Autowired
    private DeanController deanController;

    @MockBean
    private DeanService deanService;

    /**
     * Method under test: {@link DeanController#getAgendasByFacultadForDean(Long)}
     */
    @Test
    void testGetAgendasByFacultadForDean() throws Exception {
        // Arrange
        when(deanService.getAgendasByFacultadForDean(Mockito.<Long>any()))
                .thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(String.join("", "/api/dean/agenForDean/{id", System.getProperty("user.name"), "}"), 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(deanController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test:
     * {@link DeanController#getPendingAgendasForDeanApproval(Long)}
     */
    @Test
    void testGetPendingAgendasForDeanApproval() throws Exception {
        // Arrange
        when(deanService.getPendingAgendasForDeanApproval(Mockito.<Long>any()))
                .thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(String.join("", "/api/dean/historicalDean/{id", System.getProperty("user.name"), "}"), 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(deanController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test:
     * {@link DeanController#updateDeanAgendaStatus(MultipartFile, boolean, Long)}
     */
    @Test
    void testUpdateDeanAgendaStatus() throws IOException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        FacultyEntity faculty = new FacultyEntity();
        faculty.setId(1L);
        faculty.setName("Name");

        FacultyEntity faculty2 = new FacultyEntity();
        faculty2.setId(1L);
        faculty2.setName("Name");

        ProgramEntity program = new ProgramEntity();
        program.setFaculty(faculty2);
        program.setId(1L);
        program.setName("Name");

        FacultyEntity faculty3 = new FacultyEntity();
        faculty3.setId(1L);
        faculty3.setName("Name");

        FacultyEntity faculty4 = new FacultyEntity();
        faculty4.setId(1L);
        faculty4.setName("Name");

        ProgramEntity program2 = new ProgramEntity();
        program2.setFaculty(faculty4);
        program2.setId(1L);
        program2.setName("Name");

        UserEntity teacher = new UserEntity();
        teacher.setEmail("jane.doe@example.org");
        teacher.setFaculty(faculty3);
        teacher.setFirstName("Jane");
        teacher.setId(1L);
        teacher.setIdentification("Identification");
        teacher.setIsActive(true);
        teacher.setLastName("Doe");
        teacher.setPassword("iloveyou");
        teacher.setProgram(program2);
        teacher.setRoles(new HashSet<>());

        AgendasValid agendasValid = new AgendasValid();
        agendasValid.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        agendasValid.setDeanApproval(true);
        agendasValid.setFaculty(faculty);
        agendasValid.setFile("AXAXAXAX".getBytes("UTF-8"));
        agendasValid.setFileName("foo.txt");
        agendasValid.setId(1L);
        agendasValid.setProgram(program);
        agendasValid.setProgramDirectorApproval(true);
        agendasValid.setTeacher(teacher);
        Optional<AgendasValid> ofResult = Optional.of(agendasValid);

        FacultyEntity faculty5 = new FacultyEntity();
        faculty5.setId(1L);
        faculty5.setName("Name");

        FacultyEntity faculty6 = new FacultyEntity();
        faculty6.setId(1L);
        faculty6.setName("Name");

        ProgramEntity program3 = new ProgramEntity();
        program3.setFaculty(faculty6);
        program3.setId(1L);
        program3.setName("Name");

        FacultyEntity faculty7 = new FacultyEntity();
        faculty7.setId(1L);
        faculty7.setName("Name");

        FacultyEntity faculty8 = new FacultyEntity();
        faculty8.setId(1L);
        faculty8.setName("Name");

        ProgramEntity program4 = new ProgramEntity();
        program4.setFaculty(faculty8);
        program4.setId(1L);
        program4.setName("Name");

        UserEntity teacher2 = new UserEntity();
        teacher2.setEmail("jane.doe@example.org");
        teacher2.setFaculty(faculty7);
        teacher2.setFirstName("Jane");
        teacher2.setId(1L);
        teacher2.setIdentification("Identification");
        teacher2.setIsActive(true);
        teacher2.setLastName("Doe");
        teacher2.setPassword("iloveyou");
        teacher2.setProgram(program4);
        teacher2.setRoles(new HashSet<>());

        AgendasValid agendasValid2 = new AgendasValid();
        agendasValid2.setCreationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        agendasValid2.setDeanApproval(true);
        agendasValid2.setFaculty(faculty5);
        agendasValid2.setFile("AXAXAXAX".getBytes("UTF-8"));
        agendasValid2.setFileName("foo.txt");
        agendasValid2.setId(1L);
        agendasValid2.setProgram(program3);
        agendasValid2.setProgramDirectorApproval(true);
        agendasValid2.setTeacher(teacher2);
        AgendasValidRepository agendasValidRepository = mock(AgendasValidRepository.class);
        when(agendasValidRepository.save(Mockito.<AgendasValid>any())).thenReturn(agendasValid2);
        when(agendasValidRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        DeanController deanController = new DeanController(
                new DeanServiceImpl(agendasValidRepository, mock(UserRepository.class)));

        // Act
        ResponseDto<AgendasValid> actualUpdateDeanAgendaStatusResult = deanController.updateDeanAgendaStatus(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), true, 1L);

        // Assert
        verify(agendasValidRepository).findById(eq(1L));
        verify(agendasValidRepository).save(isA(AgendasValid.class));
        assertEquals("Update state aprove dean", actualUpdateDeanAgendaStatusResult.getMessage());
        assertNull(actualUpdateDeanAgendaStatusResult.getData());
        assertEquals(201, actualUpdateDeanAgendaStatusResult.getStatus());
    }
}
