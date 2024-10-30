package com.example.demo.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.FacultyEntity;
import com.example.demo.model.ProgramEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {DecanoServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DecanoServiceImplTest {
    @MockBean
    private AgendasValidRepository agendasValidRepository;

    @Autowired
    private DecanoServiceImpl decanoServiceImpl;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(decanoServiceImpl).build();
    }
    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecano(Long)}
     */
    @Test
    void testGetAgendasToDecano() {
        
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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Role");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ResponseDto<List<IAgendaDto>> actualAgendasToDecano = decanoServiceImpl.getAgendasToDecano(1L);

        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Valida tu rol", actualAgendasToDecano.getMessage());
        assertNull(actualAgendasToDecano.getData());
        assertEquals(400, actualAgendasToDecano.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecano(Long)}
     */
    @Test
    void testGetAgendasToDecano2() {
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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenThrow(new RuntimeException("Decano"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ResponseDto<List<IAgendaDto>> actualAgendasToDecano = decanoServiceImpl.getAgendasToDecano(1L);

        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDecano.getMessage());
        assertNull(actualAgendasToDecano.getData());
        assertEquals(400, actualAgendasToDecano.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecano(Long)}
     */
    @Test
    void testGetAgendasToDecano3() {
        when(agendasValidRepository.findAllForAproveDecano(Mockito.<Long>any())).thenReturn(new ArrayList<>());

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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Decano");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ResponseDto<List<IAgendaDto>> actualAgendasToDecano = decanoServiceImpl.getAgendasToDecano(1L);

        verify(agendasValidRepository).findAllForAproveDecano(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("OK", actualAgendasToDecano.getMessage());
        assertEquals(200, actualAgendasToDecano.getStatus());
        assertTrue(actualAgendasToDecano.getData().isEmpty());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecano(Long)}
     */
    @Test
    void testGetAgendasToDecano4() {
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecano = decanoServiceImpl.getAgendasToDecano(1L);

        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDecano.getMessage());
        assertNull(actualAgendasToDecano.getData());
        assertEquals(400, actualAgendasToDecano.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecano(Long)}
     */
    @Test
    void testGetAgendasToDecano5() {
        
        when(agendasValidRepository.findAllForAproveDecano(Mockito.<Long>any())).thenThrow(new RuntimeException("Decano"));

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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Decano");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecano = decanoServiceImpl.getAgendasToDecano(1L);

        
        verify(agendasValidRepository).findAllForAproveDecano(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDecano.getMessage());
        assertNull(actualAgendasToDecano.getData());
        assertEquals(400, actualAgendasToDecano.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecanoAprove(Long)}
     */
    @Test
    void testGetAgendasToDecanoAprove() {
        
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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Role");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecanoAprove = decanoServiceImpl.getAgendasToDecanoAprove(1L);

        
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Valida tu rol", actualAgendasToDecanoAprove.getMessage());
        assertNull(actualAgendasToDecanoAprove.getData());
        assertEquals(400, actualAgendasToDecanoAprove.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecanoAprove(Long)}
     */
    @Test
    void testGetAgendasToDecanoAprove2() {
        
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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenThrow(new RuntimeException("Decano"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecanoAprove = decanoServiceImpl.getAgendasToDecanoAprove(1L);

        
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDecanoAprove.getMessage());
        assertNull(actualAgendasToDecanoAprove.getData());
        assertEquals(400, actualAgendasToDecanoAprove.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecanoAprove(Long)}
     */
    @Test
    void testGetAgendasToDecanoAprove3() {
        
        when(agendasValidRepository.findAllForHistoricoDecano(Mockito.<Long>any())).thenReturn(new ArrayList<>());

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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Decano");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecanoAprove = decanoServiceImpl.getAgendasToDecanoAprove(1L);

        
        verify(agendasValidRepository).findAllForHistoricoDecano(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("OK", actualAgendasToDecanoAprove.getMessage());
        assertEquals(200, actualAgendasToDecanoAprove.getStatus());
        assertTrue(actualAgendasToDecanoAprove.getData().isEmpty());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecanoAprove(Long)}
     */
    @Test
    void testGetAgendasToDecanoAprove4() {
        
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecanoAprove = decanoServiceImpl.getAgendasToDecanoAprove(1L);

        
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDecanoAprove.getMessage());
        assertNull(actualAgendasToDecanoAprove.getData());
        assertEquals(400, actualAgendasToDecanoAprove.getStatus());
    }

    /**
     * Method under test: {@link DecanoServiceImpl#getAgendasToDecanoAprove(Long)}
     */
    @Test
    void testGetAgendasToDecanoAprove5() {
        
        when(agendasValidRepository.findAllForHistoricoDecano(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("Decano"));

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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFaculty(faculty);
        userEntity.setFirstName("Jane");
        userEntity.setId(1L);
        userEntity.setIdentification("Identification");
        userEntity.setIsActive(true);
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setProgram(program);
        userEntity.setRoles(new HashSet<>());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Decano");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<List<IAgendaDto>> actualAgendasToDecanoAprove = decanoServiceImpl.getAgendasToDecanoAprove(1L);

        
        verify(agendasValidRepository).findAllForHistoricoDecano(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDecanoAprove.getMessage());
        assertNull(actualAgendasToDecanoAprove.getData());
        assertEquals(400, actualAgendasToDecanoAprove.getStatus());
    }

    /**
     * Method under test:
     * {@link DecanoServiceImpl#updateStatusAgendaDecano(MultipartFile, boolean, Long)}
     */
    @Test
    void testUpdateStatusAgendaDecano() throws IOException {
        
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
        when(agendasValidRepository.save(Mockito.<AgendasValid>any())).thenReturn(agendasValid2);
        when(agendasValidRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<AgendasValid> actualUpdateStatusAgendaDecanoResult = decanoServiceImpl.updateStatusAgendaDecano(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), true, 1L);

        
        verify(agendasValidRepository).findById(eq(1L));
        verify(agendasValidRepository).save(isA(AgendasValid.class));
        assertEquals("Agenda creada correctamente", actualUpdateStatusAgendaDecanoResult.getMessage());
        assertNull(actualUpdateStatusAgendaDecanoResult.getData());
        assertEquals(201, actualUpdateStatusAgendaDecanoResult.getStatus());
    }

    /**
     * Method under test:
     * {@link DecanoServiceImpl#updateStatusAgendaDecano(MultipartFile, boolean, Long)}
     */
    @Test
    void testUpdateStatusAgendaDecano2() throws IOException {
        
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
        when(agendasValidRepository.save(Mockito.<AgendasValid>any()))
                .thenThrow(new RuntimeException("Agenda creada correctamente"));
        when(agendasValidRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        
        ResponseDto<AgendasValid> actualUpdateStatusAgendaDecanoResult = decanoServiceImpl.updateStatusAgendaDecano(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), true, 1L);

        
        verify(agendasValidRepository).findById(eq(1L));
        verify(agendasValidRepository).save(isA(AgendasValid.class));
        assertEquals("Ocurrio un error en el guardado de la agenda: Agenda creada correctamente",
                actualUpdateStatusAgendaDecanoResult.getMessage());
        assertNull(actualUpdateStatusAgendaDecanoResult.getData());
        assertEquals(400, actualUpdateStatusAgendaDecanoResult.getStatus());
    }
}
