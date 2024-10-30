package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DeanService;
import com.example.demo.utils.ConstanResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeanServiceImpl implements DeanService {

    private final AgendasValidRepository agendasValidRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<List<IAgendaDto>> getAgendasByFacultadForDean(Long idUsuario) {
        try {
            UserEntity user = userRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException(ConstanResponses.USER_NOT_FOUND));
            String role = userRepository.getRole(idUsuario);
            if (role.equals(ConstanResponses.Dean)){
                List<IAgendaDto> agendas = agendasValidRepository.findAllForAproveDecano(user.getFaculty().getId());
                return new ResponseDto<>(agendas, HttpStatus.OK.value(), ConstanResponses.OK);
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.VALIDATE_ROL);
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR);
        }
    }

    @Override
    public ResponseDto<List<IAgendaDto>> getPendingAgendasForDeanApproval(Long idUsuario) {
        try {
            UserEntity user = userRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException(ConstanResponses.USER_NOT_FOUND));
            String role = userRepository.getRole(idUsuario);
            if (role.equals(ConstanResponses.Dean)){
                List<IAgendaDto> agendas = agendasValidRepository.findAllForHistoricoDecano(user.getFaculty().getId());
                return new ResponseDto<>(agendas, HttpStatus.OK.value(), ConstanResponses.Dean);
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.VALIDATE_ROL);
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR);
        }
    }

    @Override
    public ResponseDto<AgendasValid> updateDeanAgendaStatus(MultipartFile file, boolean desicion, Long id) {
        try {
            AgendasValid agenda = agendasValidRepository.findById(id).orElseThrow(() -> new RuntimeException(ConstanResponses.AGENDA_NOT_FOUND));
            agenda.setFile(file.getBytes());
            agenda.setDeanApproval(desicion);
            agendasValidRepository.save(agenda);
            return new ResponseDto<>(null, HttpStatus.CREATED.value(), ConstanResponses.APROVE_DEAN);
        } catch (Exception e){
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR+e.getMessage());
        }
    }
}
