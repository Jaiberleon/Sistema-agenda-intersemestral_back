package com.example.demo.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@DisabledInAotMode
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AgendasValidServiceImplDiffblueTest {
    @MockBean
    private AgendasValidRepository agendasValidRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AgendasValidServiceImpl agendasValidServiceImpl;

    /**
     * Method under test: {@link AgendasValidServiceImpl#findUserAgendas(Long)}
     */
    @Test
    void testFindUserAgendas() {
        // Arrange
        ArrayList<IAgendaDto> iAgendaDtoList = new ArrayList<>();
        when(agendasValidRepository.findAllByuser(Mockito.<Long>any())).thenReturn(iAgendaDtoList);

        // Act
        List<IAgendaDto> actualFindUserAgendasResult = agendasValidServiceImpl.findUserAgendas(1L);

        // Assert
        verify(agendasValidRepository).findAllByuser(eq(1L));
        assertTrue(actualFindUserAgendasResult.isEmpty());
        assertSame(iAgendaDtoList, actualFindUserAgendasResult);
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findUserAgendas(Long)}
     */
    @Test
    void testFindUserAgendas2() {
        // Arrange
        when(agendasValidRepository.findAllByuser(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> agendasValidServiceImpl.findUserAgendas(1L));
        verify(agendasValidRepository).findAllByuser(eq(1L));
    }

    /**
     * Method under test: {@link AgendasValidServiceImpl#findAgendaById(Long)}
     */
    @Test
    void testFindAgendaById() {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> agendasValidServiceImpl.findAgendaById(1L));
        assertThrows(RuntimeException.class, () -> agendasValidServiceImpl.findAgendaById(2L));
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#saveAgenda(MultipartFile, Long, String, String)}
     */
    @Test
    void testSaveAgenda() throws IOException {
        // Arrange and Act
        ResponseDto<AgendasValid> actualSaveAgendaResult = agendasValidServiceImpl.saveAgenda(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Facultad",
                "Programa");

        // Assert
        assertNull(actualSaveAgendaResult.getData());
        assertEquals(400, actualSaveAgendaResult.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testFindProgramAgendasForDirector() {
        // Arrange and Act
        ResponseDto<List<IAgendaDto>> actualFindProgramAgendasForDirectorResult = agendasValidServiceImpl
                .findProgramAgendasForDirector(1L);

        // Assert
        assertEquals("Validate your role", actualFindProgramAgendasForDirectorResult.getMessage());
        assertNull(actualFindProgramAgendasForDirectorResult.getData());
        assertEquals(400, actualFindProgramAgendasForDirectorResult.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findProgramAgendasForDirector(Long)}
     */
    @Test
    void testFindProgramAgendasForDirector2() {
        // Arrange and Act
        ResponseDto<List<IAgendaDto>> actualFindProgramAgendasForDirectorResult = agendasValidServiceImpl
                .findProgramAgendasForDirector(2L);

        // Assert
        assertEquals("Service error", actualFindProgramAgendasForDirectorResult.getMessage());
        assertNull(actualFindProgramAgendasForDirectorResult.getData());
        assertEquals(400, actualFindProgramAgendasForDirectorResult.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testFindHistoricalProgramAgendasForDirector() {
        // Arrange and Act
        ResponseDto<List<IAgendaDto>> actualFindHistoricalProgramAgendasForDirectorResult = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(1L);

        // Assert
        assertEquals("Validate your role", actualFindHistoricalProgramAgendasForDirectorResult.getMessage());
        assertNull(actualFindHistoricalProgramAgendasForDirectorResult.getData());
        assertEquals(400, actualFindHistoricalProgramAgendasForDirectorResult.getStatus());
    }

    /**
     * Method under test:
     * {@link AgendasValidServiceImpl#findHistoricalProgramAgendasForDirector(Long)}
     */
    @Test
    void testFindHistoricalProgramAgendasForDirector2() {
        // Arrange and Act
        ResponseDto<List<IAgendaDto>> actualFindHistoricalProgramAgendasForDirectorResult = agendasValidServiceImpl
                .findHistoricalProgramAgendasForDirector(2L);

        // Assert
        assertEquals("Service error", actualFindHistoricalProgramAgendasForDirectorResult.getMessage());
        assertNull(actualFindHistoricalProgramAgendasForDirectorResult.getData());
        assertEquals(400, actualFindHistoricalProgramAgendasForDirectorResult.getStatus());
    }
}
