package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImp implements AdminService {
    private UserRepository userRepository;


    @Override
    public ResponseDTO<List<UserEntity>> users () {
        try {
        List<UserEntity> userEntityList = userRepository.findAll();
        return new ResponseDTO<>(userEntityList, HttpStatus.OK.value(),"Listado de usuarios");

    }catch (Exception e){
            return new ResponseDTO<>(null,HttpStatus.OK.value(),"Error al conseguir los usuarios: "+ e.getMessage());
        }
        }
}
