package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.service.AgendasValidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/agendas")
@RequiredArgsConstructor
@Tag(name = "Agenda Controller", description = "Controlador para gestionar agendas y archivos relacionados.")
public class AgendaController {

    private final AgendasValidService agendasValidService;

    @PostMapping(path = "/upload")
    @Operation(
            summary = "Subir archivo de agenda",
            description = "Permite subir un archivo de agenda asociado a un usuario, facultad y programa."
    )
    public ResponseDto<AgendasValid> uploadAgenda(
            @Parameter(description = "Archivo de la agenda", required = true) @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID del usuario", required = true) @RequestParam("userId") Long userId,
            @Parameter(description = "Facultad del usuario", required = true) @RequestParam("faculty") String faculty,
            @Parameter(description = "Programa del usuario", required = true) @RequestParam("program") String program) {
        return agendasValidService.saveAgenda(file, userId, faculty, program);
    }

    @GetMapping(path = "/user/{userId}")
    @Operation(
            summary = "Obtener agendas de usuario",
            description = "Recupera todas las agendas asociadas a un usuario específico por su ID."
    )
    public ResponseEntity<List<IAgendaDto>> getAgendasByUser(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long userId) {
        List<IAgendaDto> agendas = agendasValidService.findUserAgendas(userId);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping(path = "/download/{id}")
    @Operation(
            summary = "Descargar archivo de agenda",
            description = "Descarga el archivo de agenda en formato binario usando el ID de la agenda."
    )
    public ResponseEntity<byte[]> downloadFile(
            @Parameter(description = "ID del archivo de agenda", required = true) @PathVariable Long id) {
        AgendasValid agenda = agendasValidService.findAgendaById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + agenda.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(agenda.getFile());
    }

    @GetMapping(path = "/agenprograma/{programaId}")
    @Operation(
            summary = "Obtener agendas para el director de programa",
            description = "Obtiene agendas de un programa específico para su visualización por el director."
    )
    public ResponseDto<List<IAgendaDto>> getAgendasToDirector(
            @Parameter(description = "ID del programa", required = true) @PathVariable Long programaId) {
        return agendasValidService.findProgramAgendasForDirector(programaId);
    }

    @GetMapping(path = "/agenprogramahistorico/{programaId}")
    @Operation(
            summary = "Obtener historial de agendas para el director de programa",
            description = "Obtiene el historial de agendas de un programa específico para su visualización por el director."
    )
    public ResponseDto<List<IAgendaDto>> getAgendasToDirectorHistorico(
            @Parameter(description = "ID del programa", required = true) @PathVariable Long programaId) {
        return agendasValidService.findHistoricalProgramAgendasForDirector(programaId);
    }
}
