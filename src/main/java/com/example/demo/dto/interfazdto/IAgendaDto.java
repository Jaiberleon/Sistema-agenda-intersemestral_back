package com.example.demo.dto.interfazdto;


import java.time.LocalDateTime;

public interface IAgendaDto {
      Long getId();
      String getFileName();
      String getFaculty();
      String getProgram();
      LocalDateTime getCreationDate();
      Boolean getDeanApproval();
      Boolean getProgramDirectorApproval();
}
