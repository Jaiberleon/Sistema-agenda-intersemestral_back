package com.example.demo.service.imp;


import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
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
    public List<IAgendaDto> getAgendasByUserId(Long userId) {
        return agendasValidRepository.findAllByuser(userId);
    }

    @Override
        public AgendasValid getAgendaById(Long id) {
            return agendasValidRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
        }

    @Override
    public ResponseDto<AgendasValid> saveAgenda(MultipartFile file, Long userId, String facultad, String programa) {
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
            return new ResponseDto<>(null, HttpStatus.CREATED.value(), "Agenda creada correctamente");
        } catch (Exception e){
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Ocurrio un error en el guardado de la agenda: "+e.getMessage());
    }
    }

    @Override
    public ResponseDto<List<IAgendaDto>> getAgendasToDirector(Long programaId) {
        try {UserEntity user = userRepository.findById(programaId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String role = userRepository.getRole(programaId);
            if (role.equals("Director De programa")){
                List<IAgendaDto> agendas = agendasValidRepository.findAllToDirector(user.getPrograma().getId());
                return new ResponseDto<>(agendas,HttpStatus.OK.value(), "OK");
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Valida tu rol");
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Error en el servicio");
        }
    }

    @Override
    public ResponseDto<List<IAgendaDto>> getAgendasToDirectorHistorico(Long programaId) {
        try {UserEntity user = userRepository.findById(programaId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String role = userRepository.getRole(programaId);
            if (role.equals("Director De programa")){
                List<IAgendaDto> agendas = agendasValidRepository.findAllForHistoricDirector(user.getPrograma().getId());
                return new ResponseDto<>(agendas,HttpStatus.OK.value(), "OK");
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Valida tu rol");
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Error en el servicio");
        }
    }


}
