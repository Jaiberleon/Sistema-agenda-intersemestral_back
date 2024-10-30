package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.dto.ResponseDto;
import com.example.demo.service.AdminService;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private  AdminService adminService;
    private  MockMvc mockMvc;
    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }
    @Test
    void testGetUsers() throws Exception {
        when(adminService.getUsers()).thenReturn(new ResponseDto<>(new ArrayList<>(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin/users");

        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":[],\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }
}
