package com.example.demo.service.imp;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Override
    public ResponseDTO<UserEntity> registerUser(UserEntity user) {
        try {
            if (userRepository.findByIdentificacion(user.getIdentificacion()).isPresent() || user.getIdentificacion().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Validar el numero de identificacion ya esta registrado");
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent() || user.getEmail().isEmpty()) {
                return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "El correo ya esta registrado, intente con otra opcion");
            }
            userRepository.save(user);
            return new ResponseDTO<>(user, HttpStatus.CREATED.value(), "Registrado correctamente");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST.value(), "Ocurrio un problema en el proceso de registro");
        }

    }
}
