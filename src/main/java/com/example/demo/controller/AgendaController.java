package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
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

    @PostMapping(path = "/upload")
    public ResponseDto<AgendasValid> uploadAgenda(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("faculty") String faculty,
            @RequestParam("program") String program) {
        return agendasValidService.saveAgenda(file, userId, faculty, program);
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<IAgendaDto>> getAgendasByUser(@PathVariable Long userId) {
        List<IAgendaDto> agendas = agendasValidService.findUserAgendas(userId);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping(path = "/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        AgendasValid agenda = agendasValidService.findAgendaById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +agenda.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(agenda.getFile());
    }


    @GetMapping(path = "/agenprograma/{programaId}")
    public ResponseDto<List<IAgendaDto>> getAgendasToDirector (@PathVariable Long programaId) {
        return agendasValidService.findProgramAgendasForDirector(programaId);

    }

    @GetMapping(path = "/agenprogramahistorico/{programaId}")
    public ResponseDto<List<IAgendaDto>> getAgendasToDirectorHistorico (@PathVariable Long programaId) {
        return agendasValidService.findHistoricalProgramAgendasForDirector(programaId);

    }
}
