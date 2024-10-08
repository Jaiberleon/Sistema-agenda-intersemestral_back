package com.example.demo.service;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.FacultadDto;
import com.example.demo.dto.interfazdto.ProgramaDTO;

import java.util.List;

public interface ProgramaService {

     ResponseDTO<List<FacultadDto>> findAllFacultad ();

     ResponseDTO<List<ProgramaDTO>> findProgramaByIdFacultad (Long idFacultad);
}
