package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.service.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/dean")
public class DeanController {

    private final DeanService decanoService;

    @GetMapping("/agenForDean/{idUsuario}")
    public ResponseDto<List<IAgendaDto>> getAgendasByFacultadForDean (@PathVariable Long idUsuario) {
        return decanoService.getAgendasByFacultadForDean(idUsuario);

    }
    @GetMapping("/historicalDean/{idUsuario}")
    public ResponseDto<List<IAgendaDto>> getPendingAgendasForDeanApproval (@PathVariable Long idUsuario) {
        return decanoService.getPendingAgendasForDeanApproval(idUsuario);

    }
    @PostMapping(path = "/updateDean")
    public ResponseDto<AgendasValid> updateDeanAgendaStatus(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("decision") boolean decision, @RequestParam("idAgenda")Long idAgenda){
        return decanoService.updateDeanAgendaStatus(file,decision,idAgenda);
    }
}
