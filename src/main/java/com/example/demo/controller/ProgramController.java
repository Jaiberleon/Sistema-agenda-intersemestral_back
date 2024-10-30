package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/program")
@RequiredArgsConstructor
@CrossOrigin ("*")
public class ProgramController {

    private final ProgramService programaService;

    @GetMapping(path = "/faculties")
    public ResponseDto<List<IFacultyDto>> getAllFaculties (){
        return programaService.getAllFaculties();
    }

    @GetMapping(path = ("/Programs"))
    public ResponseDto<List<IProgramDto>> getProgramsByFacultyId (@RequestParam("facultadId") Long facultadId){
        return programaService.getProgramsByFacultyId(facultadId);
    }
    @PostMapping(path = "/update")
    public ResponseDto<AgendasValid> updateAgendaStatus(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("decision") boolean decision, @RequestParam("idAgenda")Long idAgenda){
        return programaService.updateAgendaStatus(file,decision,idAgenda);
    }
}
