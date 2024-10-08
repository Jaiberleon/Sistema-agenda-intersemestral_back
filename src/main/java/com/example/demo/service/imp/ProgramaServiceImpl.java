package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazDto.FacultadDto;
import com.example.demo.dto.interfazDto.ProgramaDTO;
import com.example.demo.model.FacultadEntity;
import com.example.demo.model.ProgramaEntity;
import com.example.demo.repository.ProgramaRepository;
import com.example.demo.service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramaServiceImpl implements ProgramaService {

    private final ProgramaRepository programaRepository;
    @Override
    public ResponseDTO<List<FacultadDto>> findAllFacultad() {
        try{
         List<FacultadDto> facultadEntities = programaRepository.finAllFacultad();
            return new ResponseDTO<>(facultadEntities,HttpStatus.OK.value(),"Exito al conseguir las facultades");
        }catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Error en el servicio: "+e.getMessage());
        }
    }

    @Override
    public ResponseDTO<List<ProgramaDTO>> findProgramaByIdFacultad(Long idFacultad) {
        try{
            List<ProgramaDTO> programas = programaRepository.findByFacultadId(idFacultad);
            return new ResponseDTO<>(programas,HttpStatus.OK.value(),"Exito al conseguir los programas");
        }catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Error en el servicio: "+e.getMessage());
        }
    }
}
