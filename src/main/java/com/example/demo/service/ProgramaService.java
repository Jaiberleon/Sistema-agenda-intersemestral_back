package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramaDto;
import com.example.demo.model.AgendasValid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProgramaService {

     ResponseDto<List<IFacultyDto>> findAllFacultad ();

     ResponseDto<List<IProgramaDto>> findProgramaByIdFacultad (Long idFacultad);
     ResponseDto<AgendasValid> updateStatusAgenda(MultipartFile file, boolean decision, Long id);
}
