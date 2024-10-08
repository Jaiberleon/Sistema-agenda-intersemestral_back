package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazDto.FacultadDto;
import com.example.demo.dto.interfazDto.ProgramaDTO;
import com.example.demo.model.FacultadEntity;
import com.example.demo.model.ProgramaEntity;

import java.util.List;

public interface ProgramaService {

     ResponseDTO<List<FacultadDto>> findAllFacultad ();

     ResponseDTO<List<ProgramaDTO>> findProgramaByIdFacultad (Long idFacultad);
}
