package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AgendasValidService {

    List<IAgendaDto> getAgendasByUserId(Long userId);
    AgendasValid getAgendaById(Long id);
    ResponseDto<AgendasValid> saveAgenda(MultipartFile file, Long userId, String facultad, String programa);
    ResponseDto<List<IAgendaDto>> getAgendasToDirector(Long programaId);
    ResponseDto<List<IAgendaDto>> getAgendasToDirectorHistorico(Long programaId);
}
