package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.AgendaDTO;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DecanoService {

    ResponseDTO<List<AgendaDTO>> getAgendasToDecano (Long facultadId);
    ResponseDTO<List<AgendaDTO>> getAgendasToDecanoAprove (Long idUsuario);
    ResponseDTO<AgendasValid> updateStatusAgendaDecano(MultipartFile file, boolean decision, Long id);

}
