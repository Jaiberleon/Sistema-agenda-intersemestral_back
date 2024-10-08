package com.example.demo.service.imp;

import com.example.demo.dto.AgendaValidDto;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.AgendasValidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AgendasValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AgendasValidServiceImpl implements AgendasValidService {
    @Autowired
    private AgendasValidRepository agendasValidRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<AgendasValid> getAgendasByUserId(Long userId) {
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
            agenda.setArchivo(file.getBytes());
            agenda.setNombreArchivo(file.getOriginalFilename());

            agenda.setFechaCreacion(LocalDateTime.now());
            agenda.setDocente(user);
            agendasValidRepository.save(agenda);
            return new ResponseDTO<>(null, HttpStatus.CREATED.value(), "Agenda creada correctamente");
        } catch (Exception e){
            return new ResponseDTO<>(null,HttpStatus.BAD_REQUEST.value(), "Ocurrio un error en el guardado de la agenda: "+e.getMessage());
    }
    }


}
