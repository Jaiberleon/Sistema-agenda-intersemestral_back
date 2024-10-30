package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AgendasValidService {

    ResponseDto<AgendasValid> saveAgenda(MultipartFile file, Long userId, String facultad, String programa);
    List<IAgendaDto> findUserAgendas(Long userId);
    AgendasValid findAgendaById(Long id);
    ResponseDto<List<IAgendaDto>> findProgramAgendasForDirector(Long programaId);
    ResponseDto<List<IAgendaDto>> findHistoricalProgramAgendasForDirector(Long programaId);
}
