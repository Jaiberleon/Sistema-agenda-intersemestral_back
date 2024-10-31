package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.dto.ResponseDto;
import com.example.demo.service.ProgramService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProgramController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProgramControllerDiffblueTest {
    @Autowired
    private ProgramController programController;

    @MockBean
    private ProgramService programService;

    /**
     * Method under test: {@link ProgramController#getAllFaculties()}
     */
    @Test
    void testGetAllFaculties() throws Exception {
        // Arrange
        when(programService.getAllFaculties())
                .thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/program/faculties");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(programController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test: {@link ProgramController#getProgramsByFacultyId(Long)}
     */
    @Test
    void testGetProgramsByFacultyId() throws Exception {
        // Arrange
        when(programService.getProgramsByFacultyId(Mockito.<Long>any()))
                .thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/program/Programs");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("facultadId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(programController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }
}
