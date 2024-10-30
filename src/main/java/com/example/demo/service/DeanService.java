package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeanService {

    ResponseDto<List<IAgendaDto>> getAgendasByFacultadForDean(Long facultadId);
    ResponseDto<List<IAgendaDto>> getPendingAgendasForDeanApproval(Long idUsuario);
    ResponseDto<AgendasValid> updateDeanAgendaStatus(MultipartFile file, boolean decision, Long id);

}
