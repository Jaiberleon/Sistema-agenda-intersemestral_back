package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.interfazdto.FacultadDto;
import com.example.demo.dto.interfazdto.ProgramaDTO;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.ProgramaRepository;
import com.example.demo.service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramaServiceImpl implements ProgramaService {
    private final AgendasValidRepository agendasValidRepository;
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

    @Override
    public ResponseDTO<AgendasValid> updateStatusAgenda(MultipartFile file, boolean desicion, Long id) {
        try {
            AgendasValid agenda = agendasValidRepository.findById(id).orElseThrow(() -> new RuntimeException("Agenda no encontrada"));
            agenda.setArchivo(file.getBytes());
            agenda.setAprobacionDirectorPrograma(desicion);
            agendasValidRepository.save(agenda);
            return new ResponseDTO<>(null, HttpStatus.CREATED.value(), "Agenda creada correctamente");
        } catch (Exception e){
            return new ResponseDTO<>(null,HttpStatus.BAD_REQUEST.value(), "Ocurrio un error en el guardado de la agenda: "+e.getMessage());
        }
    }
}
