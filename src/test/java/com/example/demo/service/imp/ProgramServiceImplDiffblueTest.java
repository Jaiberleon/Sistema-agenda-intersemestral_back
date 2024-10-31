package com.example.demo.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProgramServiceImplDiffblueTest {
  @Autowired
  private ProgramServiceImpl programServiceImpl;

  /**
   * Method under test: {@link ProgramServiceImpl#getAllFaculties()}
   */
  @Test
  void testGetAllFaculties() {
    // Arrange and Act
    ResponseDto<List<IFacultyDto>> actualAllFaculties = programServiceImpl.getAllFaculties();

    // Assert
    assertEquals("OK", actualAllFaculties.getMessage());
    assertEquals(200, actualAllFaculties.getStatus());
    assertEquals(3, actualAllFaculties.getData().size());
  }

  /**
   * Method under test: {@link ProgramServiceImpl#getProgramsByFacultyId(Long)}
   */
  @Test
  void testGetProgramsByFacultyId() {
    // Arrange and Act
    ResponseDto<List<IProgramDto>> actualProgramsByFacultyId = programServiceImpl.getProgramsByFacultyId(1L);

    // Assert
    assertEquals(
        "Service errorJDBC exception executing SQL [SELECT p.id AS id, p.name AS name FROM program p WHERE"
            + " p.faculty_id = ?] [ERROR: column p.id does not exist\n" + "  Position: 8] [n/a]; SQL [n/a]",
        actualProgramsByFacultyId.getMessage());
    assertNull(actualProgramsByFacultyId.getData());
    assertEquals(400, actualProgramsByFacultyId.getStatus());
  }
}
