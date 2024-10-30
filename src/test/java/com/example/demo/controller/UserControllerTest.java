package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    /**
     * Method under test: {@link UserController#registerUser(UserRequestDto)}
     */
    @Test
    void testRegisterUser() throws Exception {
        when(userService.registerUser(Mockito.<UserRequestDto>any()))
                .thenReturn(new ResponseDto<>(new UserEntity(), 1, "Not all who wander are lost"));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper.writeValueAsString(
                new UserRequestDto("Identificacion", "Nombre", "Apellido", "jane.doe@example.org", "iloveyou", 1L, 1L, 1L)));

        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"data\":{\"id\":null,\"identification\":null,\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null"
                                        + ",\"isActive\":true,\"faculty\":null,\"program\":null,\"roles\":[]},\"status\":1,\"message\":\"Not all who wander"
                                        + " are lost\"}"));
    }

    /**
     * Method under test: {@link UserController#login(LoginRequestDto)}
     */
    @Test
    void testLogin() throws Exception {
        when(userService.login(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponseDto<>("Data", 1, "Not all who wander are lost"));

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("jane.doe@example.org");
        loginRequestDto.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"data\":\"Data\",\"status\":1,\"message\":\"Not all who wander are lost\"}"));
    }
}
