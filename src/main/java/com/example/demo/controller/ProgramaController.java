package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.FacultadDto;
import com.example.demo.dto.interfazdto.ProgramaDTO;
import com.example.demo.service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/programa")
@RequiredArgsConstructor
@CrossOrigin ("*")
public class ProgramaController {

    private final ProgramaService programaService;

    @GetMapping(path = "/facultades")
    public ResponseDTO<List<FacultadDto>> finfAllFacultades (){
        return programaService.findAllFacultad();
    }

    @GetMapping(path = ("/Programas"))
    public ResponseDTO<List<ProgramaDTO>> findAllProgramasByFacultad (@RequestParam("facultadId") Long facultadId){
        return programaService.findProgramaByIdFacultad(facultadId);
    }
}
