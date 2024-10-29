package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.AgendaDTO;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AgendasValidService {

    List<AgendaDTO> getAgendasByUserId(Long userId);
    AgendasValid getAgendaById(Long id);
    ResponseDTO<AgendasValid> saveAgenda(MultipartFile file, Long userId,String facultad, String programa);
    ResponseDTO<List<AgendaDTO>> getAgendasToDirector(Long programaId);
    ResponseDTO<List<AgendaDTO>> getAgendasToDirectorHistorico(Long programaId);
}
