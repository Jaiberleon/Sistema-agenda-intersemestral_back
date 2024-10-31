package com.example.demo.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.UserEntity;

import java.util.List;

import org.hibernate.collection.spi.PersistentSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AdminServiceImplDiffblueTest {
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    /**
     * Method under test: {@link AdminServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers() {
        // Arrange and Act
        ResponseDto<List<UserEntity>> actualUsers = adminServiceImpl.getUsers();

        // Assert
        List<UserEntity> data = actualUsers.getData();
        assertEquals(1, data.size());
        UserEntity getResult = data.get(0);
        assertTrue(getResult.getRoles() instanceof PersistentSet);
        assertEquals("$2b$12$eiFPgYw.wj6HRXOdvSd8VODoP7qAeJ.vYEOVrNzVIEObfZzuWon0O", getResult.getPassword());
        assertEquals("123456789", getResult.getIdentification());
        assertEquals("Juan", getResult.getFirstName());
        assertEquals("Pérez", getResult.getLastName());
        assertEquals("User list retrieved successfully", actualUsers.getMessage());
        assertEquals("juan.perez@example.com", getResult.getEmail());
        assertNull(getResult.getFaculty());
        assertNull(getResult.getProgram());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(200, actualUsers.getStatus());
        assertTrue(getResult.getIsActive());
    }
}
