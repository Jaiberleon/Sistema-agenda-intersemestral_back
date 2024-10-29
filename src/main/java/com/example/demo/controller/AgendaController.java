package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.AgendaDTO;
import com.example.demo.model.AgendasValid;
import com.example.demo.service.AgendasValidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin ("*")
@RequestMapping(path = "/api/agendas")
@RequiredArgsConstructor
public class AgendaController {

private final AgendasValidService agendasValidService;

    @PostMapping("/upload")
    public ResponseDTO<AgendasValid>  saveAgenda(  @RequestParam("file") MultipartFile file,
                                                   @RequestParam("userId") Long userId,
                                                   @RequestParam("facultad") String facultad,@RequestParam("programa") String programa){
        return agendasValidService.saveAgenda(file,userId,facultad,programa);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AgendaDTO>> getAgendasByUser(@PathVariable Long userId) {
        List<AgendaDTO> agendas = agendasValidService.getAgendasByUserId(userId);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        AgendasValid agenda = agendasValidService.getAgendaById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Agenda_" + agenda.getDocente().getNombre() + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(agenda.getArchivo());
    }


    @GetMapping("/agenprograma/{programaId}")
    public ResponseDTO<List<AgendaDTO>> getAgendasToDirector (@PathVariable Long programaId) {
        return agendasValidService.getAgendasToDirector(programaId);

    }

    @GetMapping("/agenprogramahistorico/{programaId}")
    public ResponseDTO<List<AgendaDTO>> getAgendasToDirectorHistorico (@PathVariable Long programaId) {
        return agendasValidService.getAgendasToDirectorHistorico(programaId);

    }
}
