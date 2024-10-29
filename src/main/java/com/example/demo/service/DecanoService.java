package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DecanoService {

    ResponseDto<List<IAgendaDto>> getAgendasToDecano (Long facultadId);
    ResponseDto<List<IAgendaDto>> getAgendasToDecanoAprove (Long idUsuario);
    ResponseDto<AgendasValid> updateStatusAgendaDecano(MultipartFile file, boolean decision, Long id);

}
