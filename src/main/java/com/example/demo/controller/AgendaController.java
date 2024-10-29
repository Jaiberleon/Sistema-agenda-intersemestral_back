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

    @PostMapping("/upload")
    public ResponseDto<AgendasValid> saveAgenda(@RequestParam("file") MultipartFile file,
                                                @RequestParam("userId") Long userId,
                                                @RequestParam("facultad") String facultad, @RequestParam("programa") String programa){
        return agendasValidService.saveAgenda(file,userId,facultad,programa);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IAgendaDto>> getAgendasByUser(@PathVariable Long userId) {
        List<IAgendaDto> agendas = agendasValidService.getAgendasByUserId(userId);
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
    public ResponseDto<List<IAgendaDto>> getAgendasToDirector (@PathVariable Long programaId) {
        return agendasValidService.getAgendasToDirector(programaId);

    }

    @GetMapping("/agenprogramahistorico/{programaId}")
    public ResponseDto<List<IAgendaDto>> getAgendasToDirectorHistorico (@PathVariable Long programaId) {
        return agendasValidService.getAgendasToDirectorHistorico(programaId);

    }
}
