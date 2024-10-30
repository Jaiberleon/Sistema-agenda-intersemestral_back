package com.example.demo.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @MockBean
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(adminServiceImpl).build();
    }
    /**
     * Method under test: {@link AdminServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        ResponseDto<List<UserEntity>> actualUsers = adminServiceImpl.getUsers();

        // Assert
        verify(userRepository).findAll();
        assertEquals("User list retrieved successfully", actualUsers.getMessage());
        assertEquals(200, actualUsers.getStatus());
        assertTrue(actualUsers.getData().isEmpty());
    }
}
