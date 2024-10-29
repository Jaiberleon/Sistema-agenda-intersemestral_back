package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/programa")
@RequiredArgsConstructor
@CrossOrigin ("*")
public class ProgramaController {

    private final ProgramaService programaService;

    @GetMapping(path = "/facultades")
    public ResponseDto<List<IFacultyDto>> finfAllFacultades (){
        return programaService.findAllFacultad();
    }

    @GetMapping(path = ("/Programas"))
    public ResponseDto<List<IProgramaDto>> findAllProgramasByFacultad (@RequestParam("facultadId") Long facultadId){
        return programaService.findProgramaByIdFacultad(facultadId);
    }
    @PostMapping(path = "/update")
    public ResponseDto<AgendasValid> updateAdengaDirector(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("decision") boolean decision, @RequestParam("idAgenda")Long idAgenda){
        return programaService.updateStatusAgenda(file,decision,idAgenda);
    }
}
