package com.example.demo.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import java.io.UnsupportedEncodingException;
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

@ContextConfiguration(classes = {AgendasValidServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AgendasValidServiceImplTest {
    @MockBean
    private AgendasValidRepository agendasValidRepository;

    @Autowired
    private AgendasValidServiceImpl agendasValidServiceImpl;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(agendasValidServiceImpl).build();
    }
    /**
     * Method under test: {@link AgendasValidServiceImpl#findUserAgendas(Long)} (Long)}
     */
    @Test
    void testGetAgendasByUserId() {
        // Arrange
        ArrayList<IAgendaDto> iAgendaDtoList = new ArrayList<>();
        when(agendasValidRepository.findAllByuser(Mockito.<Long>any())).thenReturn(iAgendaDtoList);

        // Act
        List<IAgendaDto> actualAgendasByUserId = agendasValidServiceImpl.findUserAgendas(1L);

        // Assert
        verify(agendasValidRepository).findAllByuser(eq(1L));
        assertTrue(actualAgendasByUserId.isEmpty());
        assertSame(iAgendaDtoList, actualAgendasByUserId);
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findUserAgendas(Long)} (Long)}
     */
    @Test
    void testGetAgendasByUserId2() {
        // Arrange
        when(agendasValidRepository.findAllByuser(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> agendasValidServiceImpl.findUserAgendas(1L));
        verify(agendasValidRepository).findAllByuser(eq(1L));
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findAgendaById(Long)} (Long)}
     */
    @Test
    void testGetAgendaById() throws UnsupportedEncodingException {
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
        when(agendasValidRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        AgendasValid actualAgendaById = agendasValidServiceImpl.findAgendaById(1L);

        // Assert
        verify(agendasValidRepository).findById(eq(1L));
        assertSame(agendasValid, actualAgendaById);
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findAgendaById(Long)}
     */
    @Test
    void testGetAgendaById2() {
        // Arrange
        Optional<AgendasValid> emptyResult = Optional.empty();
        when(agendasValidRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> agendasValidServiceImpl.findAgendaById(1L));
        verify(agendasValidRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#saveAgenda(MultipartFile, Long, String, String)}
     */
    @Test
    void testSaveAgenda() throws IOException {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<AgendasValid> actualSaveAgendaResult = agendasValidServiceImpl.saveAgenda(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Facultad",
                "Programa");

        // Assert
        verify(userRepository).findById(eq(1L));
        verify(agendasValidRepository).save(isA(AgendasValid.class));
        assertEquals("Agenda creada correctamente", actualSaveAgendaResult.getMessage());
        assertNull(actualSaveAgendaResult.getData());
        assertEquals(201, actualSaveAgendaResult.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#saveAgenda(MultipartFile, Long, String, String)}
     */
    @Test
    void testSaveAgenda2() throws IOException {
        // Arrange
        when(agendasValidRepository.save(Mockito.<AgendasValid>any()))
                .thenThrow(new RuntimeException("Agenda creada correctamente"));

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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<AgendasValid> actualSaveAgendaResult = agendasValidServiceImpl.saveAgenda(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Facultad",
                "Programa");

        // Assert
        verify(userRepository).findById(eq(1L));
        verify(agendasValidRepository).save(isA(AgendasValid.class));
        assertEquals("Ocurrio un error en el guardado de la agenda: Agenda creada correctamente",
                actualSaveAgendaResult.getMessage());
        assertNull(actualSaveAgendaResult.getData());
        assertEquals(400, actualSaveAgendaResult.getStatus());
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirector() {
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

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirector = agendasValidServiceImpl.findProgramAgendasForDirector(1L);

        // Assert
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Valida tu rol", actualAgendasToDirector.getMessage());
        assertNull(actualAgendasToDirector.getData());
        assertEquals(400, actualAgendasToDirector.getStatus());
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirector2() {
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
        when(userRepository.getRole(Mockito.<Long>any())).thenThrow(new RuntimeException("Director De programa"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirector = agendasValidServiceImpl.findProgramAgendasForDirector(1L);

        // Assert
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDirector.getMessage());
        assertNull(actualAgendasToDirector.getData());
        assertEquals(400, actualAgendasToDirector.getStatus());
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirector3() {
        // Arrange
        when(agendasValidRepository.findAllToDirector(Mockito.<Long>any())).thenReturn(new ArrayList<>());

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
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Director De programa");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirector = agendasValidServiceImpl.findProgramAgendasForDirector(1L);

        // Assert
        verify(agendasValidRepository).findAllToDirector(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("OK", actualAgendasToDirector.getMessage());
        assertEquals(200, actualAgendasToDirector.getStatus());
        assertTrue(actualAgendasToDirector.getData().isEmpty());
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirector4() {
        // Arrange
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirector = agendasValidServiceImpl.findProgramAgendasForDirector(1L);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDirector.getMessage());
        assertNull(actualAgendasToDirector.getData());
        assertEquals(400, actualAgendasToDirector.getStatus());
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirector5() {
        // Arrange
        when(agendasValidRepository.findAllToDirector(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("Director De programa"));

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
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Director De programa");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirector = agendasValidServiceImpl.findProgramAgendasForDirector(1L);

        // Assert
        verify(agendasValidRepository).findAllToDirector(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDirector.getMessage());
        assertNull(actualAgendasToDirector.getData());
        assertEquals(400, actualAgendasToDirector.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirectorHistorico() {
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

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirectorHistorico = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(1L);

        // Assert
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Valida tu rol", actualAgendasToDirectorHistorico.getMessage());
        assertNull(actualAgendasToDirectorHistorico.getData());
        assertEquals(400, actualAgendasToDirectorHistorico.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirectorHistorico2() {
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
        when(userRepository.getRole(Mockito.<Long>any())).thenThrow(new RuntimeException("Director De programa"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirectorHistorico = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(1L);

        // Assert
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDirectorHistorico.getMessage());
        assertNull(actualAgendasToDirectorHistorico.getData());
        assertEquals(400, actualAgendasToDirectorHistorico.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirectorHistorico3() {
        // Arrange
        when(agendasValidRepository.findAllForHistoricDirector(Mockito.<Long>any())).thenReturn(new ArrayList<>());

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
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Director De programa");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirectorHistorico = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(1L);

        // Assert
        verify(agendasValidRepository).findAllForHistoricDirector(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("OK", actualAgendasToDirectorHistorico.getMessage());
        assertEquals(200, actualAgendasToDirectorHistorico.getStatus());
        assertTrue(actualAgendasToDirectorHistorico.getData().isEmpty());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirectorHistorico4() {
        // Arrange
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirectorHistorico = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(1L);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDirectorHistorico.getMessage());
        assertNull(actualAgendasToDirectorHistorico.getData());
        assertEquals(400, actualAgendasToDirectorHistorico.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testGetAgendasToDirectorHistorico5() {
        // Arrange
        when(agendasValidRepository.findAllForHistoricDirector(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("Director De programa"));

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
        when(userRepository.getRole(Mockito.<Long>any())).thenReturn("Director De programa");
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseDto<List<IAgendaDto>> actualAgendasToDirectorHistorico = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(1L);

        // Assert
        verify(agendasValidRepository).findAllForHistoricDirector(eq(1L));
        verify(userRepository).getRole(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals("Error en el servicio", actualAgendasToDirectorHistorico.getMessage());
        assertNull(actualAgendasToDirectorHistorico.getData());
        assertEquals(400, actualAgendasToDirectorHistorico.getStatus());
    }
}
