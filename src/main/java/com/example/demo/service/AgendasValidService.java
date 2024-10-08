package com.example.demo.service;

import com.example.demo.dto.AgendaValidDto;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AgendasValidService {

    List<AgendasValid> getAgendasByUserId(Long userId);
    AgendasValid getAgendaById(Long id);
    ResponseDTO<AgendasValid> saveAgenda(MultipartFile file, Long userId,String facultad, String programa);
}
