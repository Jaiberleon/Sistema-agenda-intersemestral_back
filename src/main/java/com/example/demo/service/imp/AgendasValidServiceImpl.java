package com.example.demo.service.imp;


import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IAgendaDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AgendasValidService;
import com.example.demo.utils.ConstanResponses;
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
    public List<IAgendaDto> findUserAgendas(Long userId) {
        return agendasValidRepository.findAllByuser(userId);
    }

    @Override
        public AgendasValid findAgendaById(Long id) {
            return agendasValidRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(ConstanResponses.AGENDA_NOT_FOUND));
        }

    @Override
    public ResponseDto<AgendasValid> saveAgenda(MultipartFile file, Long userId, String facultad, String programa) {
        try {
                UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException(ConstanResponses.USER_NOT_FOUND));

            AgendasValid agenda = new AgendasValid();
            agenda.setFileName(file.getOriginalFilename());
            agenda.setFaculty(user.getFaculty());
            agenda.setProgram(user.getProgram());
            agenda.setCreationDate(LocalDateTime.now());
            agenda.setFile(file.getBytes());
            agenda.setTeacher(user);
            agendasValidRepository.save(agenda);
            return new ResponseDto<>(null, HttpStatus.CREATED.value(), ConstanResponses.AGENDA_NOT_FOUND);
        } catch (Exception e){
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR +e.getMessage());
    }
    }

    @Override
    public ResponseDto<List<IAgendaDto>> findProgramAgendasForDirector(Long programaId) {
        try {UserEntity user = userRepository.findById(programaId)
                    .orElseThrow(() -> new RuntimeException(ConstanResponses.USER_NOT_FOUND));
            String role = userRepository.getRole(programaId);
            if (role.equals(ConstanResponses.DIRECTOR)){
                List<IAgendaDto> agendas = agendasValidRepository.findAllToDirector(user.getProgram().getId());
                return new ResponseDto<>(agendas,HttpStatus.OK.value(), ConstanResponses.OK);
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.VALIDATE_ROL);
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR);
        }
    }

    @Override
    public ResponseDto<List<IAgendaDto>> findHistoricalProgramAgendasForDirector(Long programaId) {
        try {UserEntity user = userRepository.findById(programaId)
                .orElseThrow(() -> new RuntimeException(ConstanResponses.USER_NOT_FOUND));
            String role = userRepository.getRole(programaId);
            if (role.equals(ConstanResponses.DIRECTOR)){
                List<IAgendaDto> agendas = agendasValidRepository.findAllForHistoricDirector(user.getProgram().getId());
                return new ResponseDto<>(agendas,HttpStatus.OK.value(), ConstanResponses.OK);
            }else {
                return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.VALIDATE_ROL);
            }
        } catch (Exception e) {
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR);
        }
    }


}
