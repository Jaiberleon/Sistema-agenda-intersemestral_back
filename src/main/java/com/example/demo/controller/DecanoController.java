package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.AgendaDTO;
import com.example.demo.model.AgendasValid;
import com.example.demo.service.DecanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/decano")
public class DecanoController {

    private final DecanoService decanoService;

    @GetMapping("/agentodecano/{idUsuario}")
    public ResponseDTO<List<AgendaDTO>> getAgendasToDecano (@PathVariable Long idUsuario) {
        return decanoService.getAgendasToDecano(idUsuario);

    }
    @GetMapping("/historicoDecano/{idUsuario}")
    public ResponseDTO<List<AgendaDTO>> getAgendasHistoricoToDecano (@PathVariable Long idUsuario) {
        return decanoService.getAgendasToDecanoAprove(idUsuario);

    }
    @PostMapping(path = "/updatedecano")
    public ResponseDTO<AgendasValid>  updateAdengaDirector(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("decision") boolean decision, @RequestParam("idAgenda")Long idAgenda){
        return decanoService.updateStatusAgendaDecano(file,decision,idAgenda);
    }
}
