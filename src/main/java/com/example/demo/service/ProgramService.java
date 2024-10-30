package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramDto;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProgramService {

     ResponseDto<List<IFacultyDto>> getAllFaculties();

     ResponseDto<List<IProgramDto>> getProgramsByFacultyId(Long idFacultad);
     ResponseDto<AgendasValid> updateAgendaStatus(MultipartFile file, boolean decision, Long id);
}
