package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DecanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecanoServiceImpl implements DecanoService {

    private final AgendasValidRepository agendasValidRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<List<IAgendaDto>> getAgendasToDecano(Long idUsuario) {
        try {
            UserEntity user = userRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String role = userRepository.getRole(idUsuario);
            if (role.equals("Decano")){
                List<IAgendaDto> agendas = agendasValidRepository.findAllForAproveDecano(user.getFacultad().getFacultadId());
                return new ResponseDto<>(agendas, HttpStatus.OK.value(), "OK");
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Valida tu rol");
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Error en el servicio");
        }
    }

    @Override
    public ResponseDto<List<IAgendaDto>> getAgendasToDecanoAprove(Long idUsuario) {
        try {
            UserEntity user = userRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String role = userRepository.getRole(idUsuario);
            if (role.equals("Decano")){
                List<IAgendaDto> agendas = agendasValidRepository.findAllForHistoricoDecano(user.getFacultad().getFacultadId());
                return new ResponseDto<>(agendas, HttpStatus.OK.value(), "OK");
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Valida tu rol");
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Error en el servicio");
        }
    }

    @Override
    public ResponseDto<AgendasValid> updateStatusAgendaDecano(MultipartFile file, boolean desicion, Long id) {
        try {
            AgendasValid agenda = agendasValidRepository.findById(id).orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
            agenda.setArchivo(file.getBytes());
            agenda.setAprobacionDecano(desicion);
            agendasValidRepository.save(agenda);
            return new ResponseDto<>(null, HttpStatus.CREATED.value(), "Agenda creada correctamente");
        } catch (Exception e){
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), "Ocurrio un error en el guardado de la agenda: "+e.getMessage());
        }
    }
}
