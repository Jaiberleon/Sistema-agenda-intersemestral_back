package com.example.demo.dto.interfazdto;


import java.time.LocalDateTime;

public interface IAgendaDto {
   Long getId();
   String getNombreArchivo();
   String getFacultad();
   String getPrograma();
   LocalDateTime getFechaCreacion();
   Boolean getAprobacionDecano();
   Boolean getAprobacionDirectorPrograma();

}
