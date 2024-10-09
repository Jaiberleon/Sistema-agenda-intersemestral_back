package com.example.demo.service.imp;


import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.AgendaDTO;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AgendasValidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AgendasValidServiceImpl implements AgendasValidService {

    private final AgendasValidRepository agendasValidRepository;
    private final UserRepository userRepository;


    @Override
    public List<AgendaDTO> getAgendasByUserId(Long userId) {
        return agendasValidRepository.findAllByuser(userId);
    }

    @Override
        public AgendasValid getAgendaById(Long id) {
            return agendasValidRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
        }

    @Override
    public ResponseDTO<AgendasValid> saveAgenda(MultipartFile file, Long userId, String facultad, String programa) {
        try {
                UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            AgendasValid agenda = new AgendasValid();
            agenda.setNombreArchivo(file.getOriginalFilename());
            agenda.setFacultad(user.getFacultad());
            agenda.setPrograma(user.getPrograma());
            agenda.setFechaCreacion(LocalDateTime.now());
            agenda.setArchivo(file.getBytes());
            agenda.setDocente(user);
            agendasValidRepository.save(agenda);
            return new ResponseDTO<>(null, HttpStatus.CREATED.value(), "Agenda creada correctamente");
        } catch (Exception e){
            return new ResponseDTO<>(null,HttpStatus.BAD_REQUEST.value(), "Ocurrio un error en el guardado de la agenda: "+e.getMessage());
    }
    }

    @Override
    public ResponseDTO<List<AgendaDTO>> getAgendasToDirector(Long programaId) {
        try {UserEntity user = userRepository.findById(programaId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String role = userRepository.getRole(programaId);
            if (role.equals("Director De programa")){
                List<AgendaDTO> agendas = agendasValidRepository.findAllToDirector(user.getPrograma().getId());
                return new ResponseDTO<>(agendas,HttpStatus.OK.value(), "OK");
            }else {
                return new ResponseDTO<>(null,HttpStatus.BAD_REQUEST.value(), "Valida tu rol");
            }
        } catch (Exception e) {
            return new ResponseDTO<>(null,HttpStatus.BAD_REQUEST.value(), "Error en el servicio");
        }
    }


}
