package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.interfazdto.IFacultyDto;
import com.example.demo.dto.interfazdto.IProgramDto;
import com.example.demo.model.AgendasValid;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.ProgramaRepository;
import com.example.demo.service.ProgramService;
import com.example.demo.utils.ConstanResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final AgendasValidRepository agendasValidRepository;
    private final ProgramaRepository programaRepository;
    @Override
    public ResponseDto<List<IFacultyDto>> getAllFaculties() {
        try{
         List<IFacultyDto> facultadEntities = programaRepository.finAllFacultad();
            return new ResponseDto<>(facultadEntities,HttpStatus.OK.value(), ConstanResponses.OK);
        }catch (Exception e){
            return new ResponseDto<>(null, HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR+e.getMessage());
        }
    }

    @Override
    public ResponseDto<List<IProgramDto>> getProgramsByFacultyId(Long idFacultad) {
        try{
            List<IProgramDto> programas = programaRepository.findByFacultadId(idFacultad);
            return new ResponseDto<>(programas,HttpStatus.OK.value(),ConstanResponses.OK);
        }catch (Exception e){
            return new ResponseDto<>(null, HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR+e.getMessage());
        }
    }

    @Override
    public ResponseDto<AgendasValid> updateAgendaStatus(MultipartFile file, boolean desicion, Long id) {
        try {
            AgendasValid agenda = agendasValidRepository.findById(id).orElseThrow(() -> new RuntimeException(ConstanResponses.AGENDA_NOT_FOUND));
            agenda.setFile(file.getBytes());
            agenda.setProgramDirectorApproval(desicion);
            agendasValidRepository.save(agenda);
            return new ResponseDto<>(null, HttpStatus.CREATED.value(), ConstanResponses.OK);
        } catch (Exception e){
            return new ResponseDto<>(null,HttpStatus.BAD_REQUEST.value(), ConstanResponses.SERVICE_ERROR+e.getMessage());
        }
    }
}
