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
import com.example.demo.service.AgendasValidService;
import com.example.demo.service.imp.AgendasValidServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {AgendaController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AgendaControllerTest {
    @Autowired
    private AgendaController agendaController;

    @MockBean
    private AgendasValidService agendasValidService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(agendaController).build();
    }
    /**
     * Method under test:
     * {@link AgendaController#uploadAgenda(MultipartFile, Long, String, String)}
     */
    @Test
    void testUploadAgenda() throws IOException {
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
        AgendasValidRepository agendasValidRepository = mock(AgendasValidRepository.class);
        when(agendasValidRepository.save(Mockito.<AgendasValid>any())).thenReturn(agendasValid);

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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty5);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program3);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        AgendaController agendaController = new AgendaController(
                new AgendasValidServiceImpl(agendasValidRepository, userRepository));

        ResponseDto<AgendasValid> actualUploadAgendaResult = agendaController.uploadAgenda(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Faculty",
                "Program");

        verify(userRepository).findById(eq(1L));
        verify(agendasValidRepository).save(isA(AgendasValid.class));
        assertEquals("Agenda creada correctamente", actualUploadAgendaResult.getMessage());
        assertNull(actualUploadAgendaResult.getData());
        assertEquals(201, actualUploadAgendaResult.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendaController#uploadAgenda(MultipartFile, Long, String, String)}
     */
    @Test
    void testUploadAgenda2() throws IOException {
        UserRepository userRepository = mock(UserRepository.class);
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        AgendaController agendaController = new AgendaController(
                new AgendasValidServiceImpl(mock(AgendasValidRepository.class), userRepository));

        ResponseDto<AgendasValid> actualUploadAgendaResult = agendaController.uploadAgenda(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Faculty",
                "Program");

        verify(userRepository).findById(eq(1L));
        assertNull(actualUploadAgendaResult.getData());
        assertEquals(400, actualUploadAgendaResult.getStatus());
        String expectedMessage = String.join("", "Ocurrio un error en el guardado de la agenda: ",
                System.getProperty("user.name"), " no encontrado");
        assertEquals(expectedMessage, actualUploadAgendaResult.getMessage());
    }

    /**
     * Method under test: {@link AgendaController#downloadFile(Long)}
     */
    @Test
    void testDownloadFile() throws Exception {
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
        when(agendasValidService.findAgendaById(Mockito.<Long>any())).thenReturn(agendasValid);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/agendas/download/{id}", 1L);

        MockMvcBuilders.standaloneSetup(agendaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/octet-stream"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    /**
     * Method under test: {@link AgendaController#getAgendasToDirector(Long)}
     */
    @Test
    void testGetAgendasToDirector() throws Exception {

        when(agendasValidService.findProgramAgendasForDirector(Mockito.<Long>any()))
                .thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/agendas/agenprograma/{programaId}",
                1L);

        MockMvcBuilders.standaloneSetup(agendaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test:
     * {@link AgendaController#getAgendasToDirectorHistorico(Long)}
     */
    @Test
    void testGetAgendasToDirectorHistorico() throws Exception {
        when(agendasValidService.findHistoricalProgramAgendasForDirector(Mockito.<Long>any()))
                .thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/agendas/agenprogramahistorico/{programaId}", 1L);

        MockMvcBuilders.standaloneSetup(agendaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test: {@link AgendaController#getAgendasByUser(Long)}
     */
    @Test
    void testGetAgendasByUser() throws Exception {
        when(agendasValidService.findUserAgendas(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/agendas/user/{userId}", 1L);

        MockMvcBuilders.standaloneSetup(agendaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
